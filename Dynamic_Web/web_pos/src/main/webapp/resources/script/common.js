/**
 * 
 */
 //서버로 전송할 데이터의 길이를 확인하여 적용성을 알려주는 function
 function lengthCheck(obj){
	const length =[["groupName",2,20],["groupCeo",2,5],["groupPin",6,6]];
	let result = null;
	
	for(let recordIdx=0; recordIdx<length.length; recordIdx++){
		if(obj.getAttribute("name") == length[recordIdx][0]){
			result = (obj.value.length >= length[recordIdx][1] && 
			          obj.value.length <= length[recordIdx][2])? true : false;
		
		break;
		}
	}
	return result;
}

function createForm(name, action, method){
	const form = document.createElement("form");
	if(name !== "") form.setAttribute("name",name);
	form.setAttribute("action",action);
	form.setAttribute("method",method);
	
	
	return form;
}


function movePrePage() {
		location.href = "/web_pos";
	}
	
function moveMainPage(previous){
	location.href = "/web_pos/" + previous != null? previous:"";
}