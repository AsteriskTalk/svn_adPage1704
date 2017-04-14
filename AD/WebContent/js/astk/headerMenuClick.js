	function goHome() {
			var servletPath = document.getElementById("servletPath");
			servletPath.action = "index.ad";
			servletPath.submit();
		}

		function goNews() {
			var servletPath = document.getElementById("servletPath");
			servletPath.action = "news.ad";
			servletPath.submit();
		}
		function goAD() {
			var servletPath = document.getElementById("servletPath");
			servletPath.action = "myAD.ad";
			servletPath.submit();

		}
		function goHistory() {
			var servletPath = document.getElementById("servletPath");
			servletPath.action = "myHistory.ad";
			servletPath.submit();

		}
		function goStatics() {
			var servletPath = document.getElementById("servletPath");
			servletPath.action = "myStatics.ad";
			servletPath.submit();

		}

		function goPoint() {
			var servletPath = document.getElementById("servletPath");
			servletPath.action = "index.jsp";
			servletPath.submit();
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
