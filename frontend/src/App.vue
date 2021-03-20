<template>
  <v-app>
    <v-app-bar app dense flat clipped-left height="80" color="#FFFFFF">
      <v-container fluid>
        <v-row>
          <v-img
            :src="require('./assets/buycycle.svg')"
            contain
            max-width="200"
          />
        </v-row>
        <!-- <v-row>
          <div id="demo">            
            <transition name="slide-fade">
              <v-icon v-if="show">mdi-bike</v-icon>
            </transition>
          </div>
        </v-row> -->
        <v-row>
          <v-divider inset></v-divider>
        </v-row>
      </v-container>
    </v-app-bar>
    <v-main>
      <v-container fluid class="pl-15 pt-10">
        <v-tabs vertical color="#000000">
          <v-tab> HELLO </v-tab>
          <v-tab> EBEST </v-tab>

          <v-tab-item>
            <v-card flat>
              <v-card-text>
                <Hello></Hello>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <v-tab-item>
            <v-card flat>
              <v-card-text>
                <EBest></EBest>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Hello from "./components/Hello.vue";
import EBest from "./components/EBest.vue";

export default {
  name: "App",

  components: {
    Hello,
    EBest,
  },

  data: () => ({
    mini: true,
    items: ["EBEST"],
    show: true
  }),

  methods: {},
};
</script>

<style scoped>
.v-row-margin {
  margin-left: 50px;
}

.slide-fade-enter-active {
  /* transition: all 0.3s ease; */  

}
.slide-fade-leave-active {
  /* transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1); */
  
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(100px);
  /* transition-duration: 20s; */
  transition-delay: 20s;

  opacity: 1;
}
</style>