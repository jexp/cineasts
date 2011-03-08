<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
	    <title>Cineasts.net - <decorator:title default="Welcome" /></title>
	    <decorator:head />
	    <link rel="stylesheet" type="text/css" href="/resources/style.css">
	    <%@ include file="/WEB-INF/views/includes/style.jsp" %>
	    <%@ include file="/WEB-INF/views/includes/js.jsp" %>
	</head>
	<body>
	    <%@ include file="/WEB-INF/views/includes/header-frontpage.jsp" %>
	    <div id="content">
	       	<div id="big-search-wrap">
		        <form action="#">
			        <input type="text" class="big-search" name="search" />
			        <input type="submit" class="big-search-submit" value="Search"/>
		        </form>
	        </div>
            <decorator:body />
	    </div>
	    <%@ include file="/WEB-INF/views/includes/footer.jsp" %>
	</body>
</html>