/**
 * Smart POS - Common Function
 */
 
 /* 서버로 전송할 데이터 길이의 유효성 판단 */
 function lengthCheck(obj){
	const dataLength =[["groupName",2,20],["groupCeo",2,5],["groupPin",6,6]];
	let result = null;
	for(let recordIdx=0; recordIdx<dataLength.length; recordIdx++){
		if(obj.getAttribute("name") == dataLength[recordIdx][0]){
			result = (obj.value.length >= dataLength[recordIdx][1] && 
			          obj.value.length <= dataLength[recordIdx][2])?true:false;
			break;
		} 	
	}
	
	return result;
}

/* FORM 생성 */
function createForm(name, action, method){
	const form = document.createElement("form");
	if(name != "") form.setAttribute("name", name);
	form.setAttribute("action", action);
	form.setAttribute("method", method);
	return form;
}

/* 메인페이지 이동 */
function moveMainPage(){
	location.href = "/web-pos";
}
function movePrePage(previous){
	location.href = "/web-pos/" + previous!=null?previous:"";
}
