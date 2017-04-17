	function goHome() {
			var form = document.getElementById("headerClick");
			var servletPath = document.getElementById("servletPath");
			servletPath.value = "index.ad";
			form.submit();
		}

		function goNews() {
			var form = document.getElementById("headerClick");
			var servletPath = document.getElementById("servletPath");
			servletPath.value = "news.ad";
			form.submit();
		}
		function goAD() {
			var form = document.getElementById("headerClick");
			var servletPath = document.getElementById("servletPath");
			servletPath.value = "myAD.ad";
			form.submit();

		}
		function goHistory() {
			var form = document.getElementById("headerClick");
			var servletPath = document.getElementById("servletPath");
			servletPath.value = "myHistory.ad";
			form.submit();

		}
		function goStatics() {
			var form = document.getElementById("headerClick");
			var servletPath = document.getElementById("servletPath");
			servletPath.value = "myStatics.ad";
			form.submit();

		}

		function goPoint() {
			var form = document.getElementById("headerClick");
			var servletPath = document.getElementById("servletPath");
			servletPath.value = "index.jsp";
			form.submit();
		}

		function doSignIn() {
			location.href = "signInClick.ad";
		}

		function doSignOut() {
			location.href = "doSignOut.ad";
		}

		function goMyProfile() {
			location.href = "myProfile.ad";
		}
