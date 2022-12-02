<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMBER JOIN</title>
<script src="resources/script/common.js"></script>
<script>
	function duplicate() {
		const form = createForm("","Group","get")
		const joinDataLayer = document.getElementById("joinDataLayer");
		let group = [];
		group.push(document.getElementsByName("groupName")[0]) 
		
		for (let idx = 0; idx < group.length; idx++) {
			if (lengthCheck(group[idx]) == true) {
				form.appendChild(group[idx]);
			}else {
				alert("Error Message");
				break;
			}

		joinDataLayer.appendChild(form);
		form.submit();
		}
	}
	
</script>
</head>
<body>
	<img src="https://images.velog.io/images/youngerjesus/post/74ba448d-59f7-486f-b4bf-702e8e124fdd/java.png" />
<div id = "joinDataLayer">
	<input type="text" name="groupName" placeholder="GROUP NAME" />
</div>
<div id="messageZone">${param.message}</div>
<div id = "joinEventLayer">
	<input type="button" value="next" onClick="duplicate()" />
	<input type="button" value="main" onClick="moveMainPage()" />
</div>	
</body>
</html>