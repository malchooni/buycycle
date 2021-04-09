<p align="center">
<a href="https://buycycle.name" target="_blank">
<img src="https://raw.githubusercontent.com/yalsooni/yalsooni.github.io/main/image/page/buycycle.svg" width="400px"/>
</a>
</p>

<p align="center">
증권사 API를 RESTful로 요청,응답 변환 모듈<br/><br/>
<img src="https://img.shields.io/github/v/release/yalsooni/buycycle?style=flat-square"/>
<img src="https://img.shields.io/github/release-date/yalsooni/buycycle?style=flat-square"/>
<img src="https://img.shields.io/github/last-commit/yalsooni/buycycle?style=flat-square"/>
<img src="https://img.shields.io/github/workflow/status/yalsooni/buycycle/Java%20CI%20with%20Maven?style=flat-square"/>
<img src="https://img.shields.io/github/license/yalsooni/buycycle?style=flat-square&color=yellow"/>
</p>

-------------

'Buycycle'은 증권사 API를 HTTP JSON으로 요청 및 응답 받을 수 있습니다.  
요청 받은 Json 메시지를 증권사 API 양식에 맞게 변환해 주는 자바 기반의 오픈 소스 입니다.  
HTTP RESTful을 제공함으로써 사용자는 개발 언어에 국한되지 않습니다.


<br/>

구성
-------------

<p align="center"><img src="https://buycycle.name/image/page/buycycle_arch.svg"/></p>

* backend : Spring Boot, Com4j  
  RESTful 방식으로 특정 증권사의 API를 연동합니다.  
  사용자는 http 특정 포트로 JSON으로 메시지를 주고 받습니다.
  <br/><br/>
* frontend : Vue.js Vuetifyjs  
  API 명세 및 간단한 테스트를 위해 구현 되었습니다.

<br/>

사이트
-------------

해당 사이트를 통해 바이사이클의 정보를 제공합니다.  
실행 및 예제를 확인할 수 있습니다.  
https://buycycle.name