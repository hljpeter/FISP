<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>main</title>
</head>
<frameset cols="230,*" id="mainFrame" frameborder="no" border="0">
	<frame id="leftFrame" name="leftFrame" src="leftFrame" noresize="noresize" scrolling="no"/>
	<!-- <frame id="hideBar" name="hideBar" src="hideBar" /> -->
	<frame id="rightFrame" name="_menuTarget" src="${pageContext.request.contextPath}/main/init" />
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
	