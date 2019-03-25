/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2019 Marc Gimpel
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
 */
/* $Id$ */

/**
 * Test if the given String is Empty (undefined, null or empty, i.e. '').
 * @param {string} str the String to test.
 * @returns true if the given String is empty, false otherwise.
 */
function isEmpty(str) {
	return (!str || 0 === str.length);
}

/**
 * Test if the given String is Blank (undefined, null, empty or containing
 * blank characters).
 * @param {string} str the String to test.
 * @returns true if the given String is blank, false otherwise.
 */
function isBlank(str) {
	return (!str || /^\s*$/.test(str));
}

/**
 * Set the Alert Message and type for the HTML element with id="alert".
 * @param {string} msg the Alert Message.
 * @param {string} type the Alert Type ("success", "info", "warning" or "alert").
 * @param ret what to return.
 * @returns the ret variable.
 */
function radeAlert(msg, type, ret) {
	$("#alert").html(msg);
	$("#alert").removeClass("d-none alert-success alert-danger alert-warn alert-info");
	switch(type) {
		case "success":
			$("#alert").addClass("alert-success");
			break;
		case "warning":
			$("#alert").addClass("alert-warning");
			break;
		case "info":
			$("#alert").addClass("alert-info");
			break;
		default:
			$("#alert").addClass("alert-danger");
	}
	return ret;
}

/**
 * Parse the URL query String and set appropriate error/warning/info/success
 * Messages.
 * @returns true if a Message was found, false otherwise.
 */
function urlRadeAlert() {
	var params = parseQuery(),
		msg = params["error"];
	if(msg) {
		return radeAlert(msg, "danger", true);
	}
	msg = params["warn"];
	if(msg) {
		return radeAlert(msg, "warning", true);
	}
	msg = params["info"];
	if(msg) {
		return radeAlert(msg, "info", true);
	}
	msg = params["success"];
	if(msg) {
		return radeAlert(msg, "success", true);
	}
	return false;
}

/**
 * Parse the URL query String and Return a map of Key-Value pairs.
 * @returns a map of Key-Value pairs passed in the URL query.
 */
function parseQuery() {
	var query = window.location.search,
		params = {};
	function splitUp(x) {var str = x.split(/=(.+)/); params[str[0]] = decodeURIComponent(str[1]);}
	if (query) {
		query.substring(1).split('&').forEach(splitUp);
	}
	return params;
}