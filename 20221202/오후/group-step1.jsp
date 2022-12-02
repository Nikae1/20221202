<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMBER JOIN</title>
<script src="resources/js/common.js"></script>
<script>
function serverCall(){
	const form = createForm("", "GroupDupCheck", "get");	
	const joinDataLayer = document.getElementById("joinDataLayer");
	let group = [];
	let submitResult = false;
	
	group.push(document.getElementsByName("groupName")[0]);
	
	for(let idx=0; idx<group.length; idx++){
		submitResult = lengthCheck(group[idx]);
		if(submitResult) form.appendChild(group[idx]);	
		else break;
	}
	
	if(submitResult){
		joinDataLayer.appendChild(form);
		form.submit();
	}
}

</script>

</head>
<body>
  <div id="joinDataLayer">
  	<input type="text" name="groupName" value="" placeholder="GROUP NAME" />
  	<div id="messageZone">${param.message}</div>
  </div>
  <div id="joinEventLayer">
  	<input type="button" value="NEXT"  onClick="serverCall()"/>
  	<input type="button" value="MAIN"  onClick="moveMainPage()"/>
  </div>
</body>
</html>