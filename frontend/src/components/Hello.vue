<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12">
        <p>
          <v-icon class="mr-3">mdi-comment-check-outline</v-icon>
          'buycycle'은 오픈소스이며 사용 책임은 사용자에게 있습니다.
        </p>
        <p>
          <v-icon class="mr-3">mdi-comment-check-outline</v-icon>
          <a :href="$website" target="_blank">가이드</a>
        </p>
        <p><v-icon class="mr-3">mdi-comment-check-outline</v-icon> API</p>
        <div>
          <table class="apitable">
            <tr>
              <th>Protocol</th>
              <th>메시지</th>
              <th>URL</th>
              <th>내용</th>
            </tr>
            <tr>
              <td>HTTP - GET</td>
              <td>Json</td>
              <td>/ebest/description/realtime</td>
              <td>XAReal TR 목록을 반환 한다.</td>
            </tr>
            <tr>
              <td>HTTP - GET</td>
              <td>Json</td>
              <td>/ebest/description/realtime/{trName}</td>
              <td>XAReal 특정 TR의 명세를 반환 한다.</td>
            </tr>
            <tr>
              <td>HTTP - GET</td>
              <td>Json</td>
              <td>/ebest/description/queries</td>
              <td>XAQuery TR 목록을 반환 한다.</td>
            </tr>
            <tr>
              <td>HTTP - GET</td>
              <td>Json</td>
              <td>/ebest/description/queries/{trName}</td>
              <td>XAQuery 특정 TR의 명세를 반환 한다.</td>
            </tr>
            <tr>
              <td>HTTP - GET</td>
              <td>Json</td>
              <td>/ebest/request-messages/{trName}</td>
              <td>특정 TR의 요청 샘플 메시지를 반환 한다.</td>
            </tr>
            <tr>
              <td>HTTP - POST <br/> Content-Type: application/json</td>
              <td>Json</td>
              <td>/ebest/queries</td>
              <td>XAQuery (Reqest/Response) TR을 호출 한다.</td>
            </tr>
            <tr>
              <td>WebSocket</td>
              <td>Json</td>
              <td>/ebest/real</td>
              <td>XAReal TR을 호출 한다.</td>
            </tr>
          </table>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
};
</script>

<style scoped>
a:link {
  color: #d61515;
  text-decoration: none;
}

a:visited {
  color: #d61515;
  text-decoration: none;
}

a:hover {
  color: #b3611f;
  text-decoration: underline;
}

.apitable {
  width: 100%;
  border-collapse: collapse;
  margin: auto;
  font-size: 11pt;
}

.apitable th {
  background: #eeeeee;
}
.apitable th,
.apitable td {
  border: 1px solid black;
  padding-left: 10px;
}
</style>
  