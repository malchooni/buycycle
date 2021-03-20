<template>
  <!-- <v-container> -->
    <v-row class="mt-5">
      <v-col cols="6">
        <v-textarea height="500" outlined v-model="reqmsg" label="Request"></v-textarea>        
      </v-col>
      <v-col cols="6">
        <v-textarea height="500" outlined v-model="resmsg" label="Response"></v-textarea>
      </v-col>
    </v-row>
  <!-- </v-container> -->
</template>

<script>
export default {
    data(){
        return {
            reqmsg: '',
            resmsg: ''
        }
    },
    methods: {
        setReqMsg: function(reqMsg){
            this.reqmsg = reqMsg;
        },
        setResMsg: function(resMsg){
            this.resmsg = resMsg;
        }
    }    
};
</script>

<style scoped>
</style>