<jsp:include page="../index.jsp"/>
<hr/>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:form action="loginProcess">
    <s:textfield name="username" label="Name"/>
    <s:password name="password" label="Password"/>
    <s:submit value="login"/>
</s:form>