<%@ page language="java" 
	import="javax.servlet.*,
			javax.servlet.http.*,
			javax.servlet.jsp.*,
			javax.servlet.descriptor.*,
			java.util.*,
			java.text.*,
			java.io.*"
%><%
/**
 * jspinfo v1.0 - created by NilsCode
 * 
 * This is a one-file jsp engine info file. It displays data about its runtime environment
 * like PHP's phpinfo().
 * This file comes without any warrenty and you use it on your own risk.
 */
String jspinfo_version = "1.0";
%><%!
 	// format function for displaying 'null' values correctly
	public String displayNullable(Object s) {
		if (s == null) {
			return "<span class=\"italic\">(null)</span>";
		} else {
			String tmpStr = s.toString();
			if (tmpStr.length() == 0) {
			    tmpStr = "<span class=\"italic\">(empty string)</span>";
			} else {
			    tmpStr = tmpStr.replaceAll("\r", "<span class=\"light\">&#92;r</span>");
			    tmpStr = tmpStr.replaceAll("\n", "<span class=\"light\">&#92;n</span>");
			    tmpStr = tmpStr.replaceAll("\t", "<span class=\"light\">&#92;t</span>");
			}
			return tmpStr;
		}
	}
%>
<html>
<head>
	<title>jspinfo() v<%= jspinfo_version %></title>
	<style type="text/css">
	<!--
		body 			{ background-color: #ffffff; }
		table			{ border-width: 0px; width: 100%; }
		td.category		{ background-color: #f8673d; vertical-align:top; padding: 2px;
							font-family: Tahoma, Arial; font-size: 10pt; color: #ffffff; font-weight: bold; }
		td.listCaption	{ background-color: #999999; vertical-align:top; padding: 2px;
							font-family: Tahoma, Arial; font-size: 10pt; color: #ffffff; font-weight: bold; }
		td.listKey		{ width: 20%; background-color: #dddddd; vertical-align:top; padding: 2px;
							font-family: Tahoma, Arial; font-size: 10pt; color: #000000; }
		td.listValue	{ width: 80%; background-color: #eeeeee; vertical-align:top; padding: 2px;
							font-family: Tahoma, Arial; font-size: 10pt; color: #000000; }
		td.space		{ background-color: #ffffff; vertical-align:top; padding: 2px;
							font-family: Tahoma, Arial; font-size: 10pt; color: #ffffff; }
		td.captionbig	{ background-color: #6ab7db; vertical-align:top; padding: 2px;
							font-family: Tahoma, Arial; font-size: 24pt; color: #ffffff; font-weight: bold; }
		td.caption		{ background-color: #6ab7db; vertical-align:top; text-align: right; padding: 2px;
							font-family: Tahoma, Arial; font-size: 10pt; color: #ffffff; font-weight: bold; }
		td.captionimage	{ background-color: #6ab7db; text-align: center; padding: 2px;
							font-size: 1pt; }
		span.italic		{ font-style: italic; color: #666666; }
        span.light		{ color: #666666; }
	-->
	</style>
</head>
<body>
<table>
	<tr>
		<td width="99%" class="captionbig">jspinfo() v<%= jspinfo_version %></td>
		<td width="1%" class="captionimage"><img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFgAAAAfCAMAAABUFvrSAAABIFBMVEX///89eZ3////Y/v/S9f9SdoSlwMlZhZy/8P+jxtmu2Op9oLF4na+Pw9yiwtFhiJp+sclcjaDM8P6Rs8ROh6JZj6Sr4/twl6m55/2nxtna//+bvMba9P+j0+OhwM5+g3yYtMCKudCLvNJsobmOsbzF8f/F5vhIdobQ6PTT///X9PdZl62/v792orShxNR6obZkj6FnjJ+83ut9rsI1fZVlmbFsqMCKrb6XtbitydbN9//I7/5yoridxd5Rkq/V+v9HdIm40uOGo7WUsb92q7tzs8x/qLx2rsdVianf3+FXj7DC5vaWtcqfn6HH8f/I9v96m6Q8doxTeolnjKaDuNdPeIyaucu+4/N0p8KipKFDcoyMyOyf2PW79P9OdIc9Qz/XuvKHAAAAAXRSTlPDQotLbAAAAdxJREFUeF7FlIWq5EAURKcqGXeX5+7uvu7uvv//F6/7shd6IL0vbHZ4BwJVudxD6ISknq6PhJepdY6EqdGL02lzUdGaULyc9ojNIIlY10UvWasZJRA7XkWrmJM9sYj0iV1v8jO2VipSk56xECm+pe/4AshyiI0709NLW34x4BTv7bsAvtFlE3PkOfIJxSXMcogQIHkFhMmOAljhEA/QpyGP2v8VjwM6GI8Uw0Ce7ADdE9tOH5m0LAN7hfeA+9IgpiaAJg0VPKZQQ98vLkOQJjRIp86omHVY6iSLKFAooOh/ecCPIPj8xuaHOa4cYE/Fcwsso0UbA3LmSTvHhUs0ySoaFBqo/k38UzMNi2ipmGQA6GQNz0n2sCNd9+EXHwLbR7u6rlKtKnbHg3hiHsHwSTe5f6OYwCCOmMx9x0fNu/tr0eJnWJSjeG/7awohsn7xQYfs4J3NJvVabyeixbNo5/hKXl4RE3G+CmHMZqHLaDG3YWmTHEONwh4m/eJCCahXJH/IIvuCPjHnAczbsAro+qpf7Oa4fNF/xSGjxeV/FIdAIBunf8QZF8IwSadKjkMqjw2yhM1UJSPiJRcC1QzpVMlxILd+HR+nS3ZbxGcuFKhVckxo0G0j/jo1En5fA7pQZhUn9utXAAAAAElFTkSuQmCC" alt="jspinfo()" /></td>
	</tr>
	<tr>
		<td colspan="2" class="caption">::: the ultimative one-file jsp info file :::</td>
	</tr>
</table>
&nbsp;
<table>
	<tr>
		<td class="category" colspan="2">System Properties</td>
	</tr>
<%
	Properties sysProperties = System.getProperties();
	Object[] keysObj = sysProperties.keySet().toArray();
	Arrays.sort(keysObj);
%>
	<tr>
		<td class="listCaption">Property Key</td>
		<td class="listCaption">Property Value</td>
	</tr>
<%
	for (int i = 0; i < keysObj.length; i++) {
%>
	<tr>
		<td class="listKey"><%= keysObj[i] %></td>
		<td class="listValue"><%
			Object val = sysProperties.get(keysObj[i]);
			out.print(displayNullable(val));
			//String valStr = String.valueOf(val);
			//if (valStr == null || valStr.trim().equals("")) {
			//	valStr = "&nbsp;";
			//}
			//out.print(valStr);
		 %></td>
	</tr>
<%
	}
%>
	<tr>
		<td class="space" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="category" colspan="2">HTTP Request - Headers</td>
	</tr>
<%
	Enumeration e = request.getHeaderNames();
%>
	<tr>
		<td class="listCaption">Name</td>
		<td class="listCaption">Value</td>
	</tr>
<%
	while (e.hasMoreElements()) {
		String name = (String) e.nextElement();
		String value = request.getHeader(name);
%>
	<tr>
		<td class="listKey"><%= name %></td>
		<td class="listValue"><%= value %></td>
	</tr>
<%
	}
%>
	<tr>
		<td class="space" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="category" colspan="2">HTTP Request - Infos</td>
	</tr>
	<tr>
		<td class="listCaption">Name</td>
		<td class="listCaption">Value</td>
	</tr>
	<tr>
		<td class="listKey">character encoding</td>
		<td class="listValue"><%= displayNullable(request.getCharacterEncoding()) %></td>
	</tr>
	<tr>
		<td class="listKey">content type</td>
		<td class="listValue"><%= displayNullable(request.getContentType()) %></td>
	</tr>
	<tr>
		<td class="listKey">locale</td>
		<td class="listValue"><%= displayNullable(request.getLocale()) %>
	</tr>
    <tr>
        <td class="listKey">all locales</td>
        <td class="listValue"><%
		Enumeration localeEnum = request.getLocales();
		if (localeEnum != null) {
		  while (localeEnum.hasMoreElements()) {
		      Locale oneLocale = (Locale)localeEnum.nextElement();
		      out.print(displayNullable(oneLocale) + " ");
		  }
		}
		%></td>
	</tr>
	<tr>
		<td class="listKey">http protocol</td>
		<td class="listValue"><%= displayNullable(request.getProtocol()) %></td>
	</tr>
	<tr>
		<td class="listKey">remote address</td>
		<td class="listValue"><%= displayNullable(request.getRemoteAddr()) %></td>
	</tr>
	<tr>
		<td class="listKey">remote host</td>
		<td class="listValue"><%= displayNullable(request.getRemoteHost()) %></td>
	</tr>
	<tr>
		<td class="listKey">scheme</td>
		<td class="listValue"><%= displayNullable(request.getScheme()) %></td>
	</tr>
	<tr>
		<td class="listKey">server name</td>
		<td class="listValue"><%= displayNullable(request.getServerName()) %></td>
	</tr>
	<tr>
		<td class="listKey">server port</td>
		<td class="listValue"><%= request.getServerPort() %></td>
	</tr>
	<tr>
		<td class="listKey">using secure connection</td>
		<td class="listValue"><%= request.isSecure() %></td>
	</tr>
	<tr>
		<td class="space" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="category" colspan="2">Servlet / JSP Data</td>
	</tr>
	<tr>
		<td class="listCaption">Name</td>
		<td class="listCaption">Value</td>
	</tr>
	<tr>
		<td class="listKey">authentication type</td>
		<td class="listValue"><%= displayNullable(request.getAuthType()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">context path</td>
		<td class="listValue"><%= displayNullable(request.getContextPath()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">cookies</td>
		<td class="listValue"><%
			Cookie[] cookies = request.getCookies();
			if (cookies != null && cookies.length > 0) {
				for (int i = 0; i < cookies.length; i++) {
					Cookie cookie = cookies[i];
					out.println("Name: '"+cookie.getName()+"'<br />");
					out.println("Value: '"+cookie.getValue()+"'<br />");
					if (cookie.getDomain() != null) out.println("Domain: '"+cookie.getDomain()+"'<br />");
					if (cookie.getComment() != null) out.println("Comment: '"+cookie.getComment()+"'<br />");
					if (cookie.getPath() != null) out.println("Path: '"+cookie.getPath()+"'<br />");
					out.println("Max Age: '"+cookie.getMaxAge()+"' sec.<br />");
					if (cookie.getVersion() != 0) out.println("Version: '"+cookie.getVersion()+"'<br />");
					if (cookies.length > 1) out.println("<br />");
				}
			} else {
				out.print("&nbsp;");
			}
		 %></td>
	</tr>
	<tr>
		<td class="listKey">http method</td>
		<td class="listValue"><%= displayNullable(request.getMethod()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">path info</td>
		<td class="listValue"><%= displayNullable(request.getPathInfo()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">path (translated)</td>
		<td class="listValue"><%= displayNullable(request.getPathTranslated()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">query string</td>
		<td class="listValue"><%= displayNullable(request.getQueryString()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">remote user</td>
		<td class="listValue"><%= displayNullable(request.getRemoteUser()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">requested session id</td>
		<td class="listValue"><%= displayNullable(request.getRequestedSessionId()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">request uri</td>
		<td class="listValue"><%= displayNullable(request.getRequestURI()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">request url</td>
		<td class="listValue"><%= displayNullable(request.getRequestURL().toString()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">servlet path</td>
		<td class="listValue"><%= displayNullable(request.getServletPath()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">session - created at</td>
		<td class="listValue"><%
			long seesionCreated = session.getCreationTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy / HH:mm:ss.SSSS");
			String sessionCreatedStr = sdf.format(new Date(seesionCreated));
			out.print(sessionCreatedStr);
		%>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">session - last accessed</td>
		<td class="listValue"><%
			long sessionAccessed = session.getLastAccessedTime();
			String sessionAccessedStr = sdf.format(new Date(sessionAccessed));
			out.print(sessionAccessedStr);
		%>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">session - attributes</td>
		<td class="listValue"><%
			e = session.getAttributeNames();
			if (e.hasMoreElements()) {
				while (e.hasMoreElements()) {
					String name = (String) e.nextElement();
					Object value = session.getAttribute(name);
					out.println(name + ": '"+value+"'<br />");
				}
			} else {
				out.print("<span class=\"italic\">(no attribute objects bound to session)</span>");
			}
		%></td>
	</tr>
	<%
		ServletContext context = session.getServletContext();
		if (context != null) {
	%>
	<tr>
		<td class="listKey">context - servlet api - version</td>
		<td class="listValue"><%= context.getMajorVersion() + "." + context.getMinorVersion() %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">context - real path</td>
		<td class="listValue">'<%= request.getServletPath() %>':<br /><%= context.getRealPath(request.getServletPath()) %><br />&nbsp;<br />
		'/':<br /><%= context.getRealPath("/") %></td>
	</tr>
	<tr>
		<td class="listKey">context - server info</td>
		<td class="listValue"><%= displayNullable(context.getServerInfo()) %>&nbsp;</td>
	</tr>
	<tr>
		<td class="listKey">context - servlet context name</td>
		<td class="listValue"><%= displayNullable(context.getServletContextName()) %>&nbsp;</td>
	</tr>
	<%
			if (context.getMajorVersion() >= 3) {
				JspConfigDescriptor jspConfig = context.getJspConfigDescriptor();
				if (jspConfig != null) {
					Collection<TaglibDescriptor> c = jspConfig.getTaglibs();
					if (c != null) {
						for (TaglibDescriptor tld : c) {
							out.print(tld.getTaglibURI() + "<br>");
						}
					}
	%>
	<tr>
		<td class="listKey">JSP Config - TagLibs</td>
		<td class="listValue"><%= displayNullable(context.getJspConfigDescriptor()) %>&nbsp;</td>
	</tr>
	<%
				}
			}
		}
		File f = new File("");
	%>
	<tr>
		<td class="listKey">current directory</td>
		<td class="listValue"><%= f.getAbsolutePath() %>&nbsp;</td>
	</tr>
</table>
</body>
</html>
