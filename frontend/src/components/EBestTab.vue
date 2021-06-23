<template>
  <v-container fluid>
    <v-tabs v-model="tab" color="basil" grow >
      <v-tab> xaReal 실시간 </v-tab>
      <v-tab> xaQuery 요청 / 응답 </v-tab>

      <v-tab-item>
        <real ref="realTable" restype="realtime"></real>
      </v-tab-item>
      <v-tab-item eager>
        <query ref="queryTable" restype="queries"></query>
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
import EBestTable from "./EBestTable.vue";
import axios from 'axios';

export default {
  components: {
    'real': EBestTable,
    'query': EBestTable
  },
  created(){
    this.getRealList();
    this.getQueryList();
  },
  data() {
    return {
      tab:{}
    }
  },
  methods:{
    getRealList() {
      axios
        .get('/ebest/description/realtime')
        .then(response => {
          if (response && response.data !== null) {
            this.$refs.realTable.setData(response.data);
            this.$refs.realTable.loaded();
          }
        })
        .catch(error => {
          console.log('error : ', error);
        });
    },
    getQueryList() {
      axios
        .get('/ebest/description/queries')
        .then(response => {
          if (response && response.data !== null) {
            this.$refs.queryTable.setData(response.data);
            this.$refs.queryTable.loaded();
          }
        })
        .catch(error => {
          console.log('error : ', error);
        });
    }
  }

};
</script>

<style scoped>
/* Helper classes */
.basil {
  background-color: #a79d6f !important;
}
</style>