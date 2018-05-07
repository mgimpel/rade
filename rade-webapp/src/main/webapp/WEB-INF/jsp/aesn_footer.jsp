<%/*
 *  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2018 Marc Gimpel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */%>
<%/* $Id$ */%>
<%@ page import="java.text.*,java.util.*" %>
<%@ page import="fr.aesn.rade.webapp.config.Version" %>
		</td>
	</tr>
	<tr id="footer">
		<td><%
			SimpleDateFormat ft = new SimpleDateFormat ("d MMMMM yyyy");
			out.print(ft.format(new Date()));
		%></td>
		<td>
			<div id="footer_r">
				<a href="#" onclick="window.open('http://www.homol.eau-seine-normandie.fr/index.php?id=3669')">Contact</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="#" onclick="window.open('AproposHabilitationsAction.do?actions=init','popUpWindow','height=160,width=550,left=200,top=100,resizable=true,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes');">À propos de</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="#" onclick="window.open('http://www.homol.eau-seine-normandie.fr/index.php?id=3668')">Glossaire</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="#" onclick="window.open('http://www.homol.eau-seine-normandie.fr/index.php?id=3670')">FAQ</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<%out.print(Version.PROJECT_VERSION);%>
			</div>
		</td>
	</tr>
	</tbody>
</table>
</body>
</html>