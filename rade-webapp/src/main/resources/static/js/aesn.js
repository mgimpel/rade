function isEmpty(str) {
	return (!str || 0 === str.length);
}
function isBlank(str) {
	return (!str || /^\s*$/.test(str));
}
function radeAlert(msg, level, ret) {
	$("#alert").html(msg);
	$("#alert").removeClass("d-none alert-success alert-danger alert-warn alert-info");
	switch(level) {
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
function urlRadeAlert() {
	var url = new URL(window.location.href);
	var error = url.searchParams.get("error");
	if(error) {
		return radeAlert(error, "danger", true);
	}
	var warn = url.searchParams.get("warn");
	if(warn) {
		return radeAlert(warn, "warning", true);
	}
	var info = url.searchParams.get("info");
	if(info) {
		return radeAlert(info, "info", true);
	}
	var success = url.searchParams.get("success");
	if(success) {
		return radeAlert(success, "success", true);
	}
}
