<template>
  <v-card>
    <v-dialog persistent scrollable v-model="dialog" max-width="1024">
      <template>
        <v-card>
          <v-toolbar color="#DDDDDD" flat>
            <v-tabs v-model="tabs">
              <v-tabs-slider color="#8D818C"></v-tabs-slider>
              <v-tab> Description </v-tab>
              <v-tab> Test </v-tab>
            </v-tabs>
          </v-toolbar>
          <v-card-text style="height: 600px">
            <v-tabs-items v-model="tabs">
              <v-tab-item eager>
                <div v-for="(value, name) in resdesc" v-bind:key="name">
                  <restable :name="name" :items="value"></restable>
                </div>
              </v-tab-item>
              <v-tab-item eager> <testbox ref="testbox"></testbox> </v-tab-item>
            </v-tabs-items>
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-alert
              dense
              outlined
              type="error"
              width="100%"
              height="40"
              style="font-size: 9pt"
              class="mt-4 mr-4"
              v-show="this.tabs === 1 && this.restype === 'real'"
            >
              'Close' 버튼을 누르면 웹소켓 연결이 종료 됩니다.
            </v-alert>
            <v-btn
              v-show="this.tabs === 1"
              outlined
              text
              @click="request()"
              :request="request"
              >Request</v-btn
            >
            <v-btn outlined text @click="close()">Close</v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
    <v-card-title>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      dense
      :headers="headers"
      :items="items"
      :search="search"
      :loading="loading"
      loading-text="Loading... Please wait"
      @click:row="showDetail"
    ></v-data-table>
  </v-card>
</template>

<script>
import axios from "axios";
import restable from "./EBestResTable.vue";
import testbox from "./TestBox.vue";

export default {
  props: ["restype"],
  components: {
    restable,
    testbox,
  },
  data() {
    return {
      search: "",
      loading: true,
      headers: [],
      items: [],
      dialog: false,
      resdesc: null,
      tabs: {},
      ws: null,
    };
  },
  methods: {
    loaded() {
      this.loading = false;
    },
    setData(itemtable) {
      this.headers = itemtable.headers;
      this.items = itemtable.items;
    },
    showDetail: function (item) {
      this.dialog = true;
      this.getDescription(item);
      this.getDefaultReqMsg(item);
      if (this.restype === "realtime") {
        this.connectWebSocket();
      }
    },
    getDescription: function (item) {
      axios
        .get("/ebest/description/" + this.restype + "/" + item.name)
        .then((response) => {
          if (response && response.data !== null) {
            this.resdesc = response.data.resDesc;
          }
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    },
    getDefaultReqMsg: function (item) {
      axios
        .get("/ebest/request-messages/" + item.name)
        .then((response) => {
          if (response && response.data !== null) {
            this.$refs.testbox.setReqMsg(
              JSON.stringify(response.data, null, 2)
            );
          }
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    },
    connectWebSocket: function () {      
      const that = this;
      this.ws = new WebSocket("ws://" + location.host + "/ebest/realtime");
      this.ws.testbox = this.$refs.testbox;
      this.ws.onopen = function (e) {
        console.log("connect success");
        console.log(e);
      };
      this.ws.onmessage = function (event) {
        // that.$refs.testbox.setResMsg(event.data);
        that.$refs.testbox.setResMsg(
          JSON.stringify(JSON.parse(event.data), null, 2)
        );
        
      };
      this.ws.onclose = function (event) {
        if (event.wasClean) {
          console.log("close success");
        } else {
          console.log("close error");
        }
      };
      this.ws.onerror = function (error) {
        console.log(error);
      };
    },
    request: function () {
      switch (this.restype) {
        case "realtime":
          this.requestReal();
          break;
        case "queries":
          this.requestQuery();
          break;
      }
    },
    requestReal: function () {
      this.ws.send(this.$refs.testbox.reqmsg);
    },
    requestQuery: function () {
      axios
        .post("/ebest/queries", this.$refs.testbox.reqmsg, {
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          if (response && response.data !== null) {
            this.$refs.testbox.setResMsg(
              JSON.stringify(response.data, null, 2)
            );
          }
        })
        .catch((error) => {
          console.log("error : ", error);
        });
    },
    close: function () {
      this.dialog = false;
      this.resdesc = null;
      this.tabs = 0;
      if (this.restype === "realtime") {
        this.ws.close(1000, "test done.");
        this.ws = null;
      }
      this.$refs.testbox.setReqMsg("");
      this.$refs.testbox.setResMsg("");
    },
  },
};
</script>