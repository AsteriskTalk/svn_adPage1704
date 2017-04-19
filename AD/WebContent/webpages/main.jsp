<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- <center><jsp:include page="/main/advertisement.html" flush="false" /></center> <br>
<center><jsp:include page="/main/information.html" flush="false" /></center> <br> 
<center><jsp:include page="/main/example.html" flush="false" /></center> <br>
<center><jsp:include page="/common/download.html" flush="false" /></center> --%>



<div id="wrap">

	<div id="visual">
		<div id="mySwipe" class='swipe'>
			<ul class="touch_banner swipe-wrap">
				<!-- 배너 목록 -->
				<li><a href="#"><img src="images/visual_img_1.jpg" alt="" /></a></li>
				<li><a href="#"><img src="images/visual_img_2.jpg" alt="" /></a></li>
				<li><a href="#"><img src="images/visual_img_3.jpg" alt="" /></a></li>
			</ul>
		</div>
		<ul class="touch_bullet">
			<!-- 배너 위치 표시 -->
			<li><img src="images/visual_bullet_on.png" class="active" alt="" /></li>
			<li><img src="images/visual_bullet_off.png" alt="" /></li>
			<li><img src="images/visual_bullet_off.png" alt="" /></li>
		</ul>
		<p class="touch_left_btn">
			<!-- 이전 버튼 -->
			<a href="#"> <img src="images/visual_btn_left.png" alt="이전 배너" />
			</a>
		</p>
		<p class="touch_right_btn">
			<!-- 이전 버튼 -->
			<a href="#"> <img src="images/visual_btn_right.png" alt="다음 배너" />
			</a>
		</p>
	</div>
	<hr />

</div>
<div id="wrap">
	<div id="contents">
		<div id="contents_top">
			<div id="roll_banner_wrap">
				<h3>
					<img src="images/pop_title.gif" alt="알림판" />
				</h3>
				<dl>
					<dt class="roll_btn1">
						<a href="#" class="active"> <img
							src="images/pop_btn_1_over.gif" alt="버튼1" />
						</a>
					</dt>
					<dd>
						<a href="#"><img src="images/pop_banner_1.gif" alt="배너1" /></a>
					</dd>
					<dt class="roll_btn2">
						<a href="#"><img src="images/pop_btn_2_out.gif" alt="버튼2" /></a>
					</dt>
					<dd>
						<a href="#"><img src="images/pop_banner_2.gif" alt="배너2" /></a>
					</dd>
					<dt class="roll_btn3">
						<a href="#"><img src="images/pop_btn_3_out.gif" alt="버튼3" /></a>
					</dt>
					<dd>
						<a href="#"><img src="images/pop_banner_3.gif" alt="배너3" /></a>
					</dd>
					<dt class="roll_btn4">
						<a href="#"><img src="images/pop_btn_4_out.gif" alt="버튼4" /></a>
					</dt>
					<dd>
						<a href="#"><img src="images/pop_banner_4.gif" alt="배너4" /></a>
					</dd>
				</dl>
				<p class="ctl_btn">
					<a href="#" class="playBtn"> <img
						src="images/pop_btn_play_on.gif" alt="재생 버튼" />
					</a> <a href="#" class="stopBtn"> <img
						src="images/pop_btn_stop_off.gif" alt="정지 버튼" />
					</a>
				</p>
			</div>
			<dl id="tabmenu">
				<dt class="tab_btn1">
					<a href="#"><img src="images/tab_btn_1_over.gif" alt="공지사항" /></a>
				</dt>
				<dd>
					<ul>
						<li><a href="#">공지사항 관련된 내용입니다.</a><span>2014-03-01</span></li>
						<li><a href="#">공지사항 관련된 내용입니다.</a><span>2014-03-01</span></li>
						<li><a href="#">공지사항 관련된 내용입니다.</a><span>2014-03-01</span></li>
						<li><a href="#">공지사항 관련된 내용입니다.</a><span>2014-03-01</span></li>
						<li><a href="#">공지사항 관련된 내용입니다.</a><span>2014-03-01</span></li>
					</ul>
					<p>
						<a href="#"><img src="images/tab_more_btn.gif" alt="더보기" /></a>
					</p>
				</dd>
				<dt class="tab_btn2">
					<a href="#"><img src="images/tab_btn_2_out.gif" alt="질문과답변" /></a>
				</dt>
				<dd>
					<ul>
						<li><a href="#">질문과 답변 관련된 내용입니다.</a> <span>2014-03-01</span>
						</li>
						<li><a href="#">질문과 답변 관련된 내용입니다.</a> <span>2014-03-01</span>
						</li>
						<li><a href="#">질문과 답변 관련된 내용입니다.</a> <span>2014-03-01</span>
						</li>
						<li><a href="#">질문과 답변 관련된 내용입니다.</a> <span>2014-03-01</span>
						</li>
						<li><a href="#">질문과 답변 관련된 내용입니다.</a> <span>2014-03-01</span>
						</li>
					</ul>
					<p>
						<a href="#"><img src="images/tab_more_btn.gif" alt="더보기" /></a>
					</p>
				</dd>
				<dt class="tab_btn3">
					<a href="#"><img src="images/tab_btn_3_out.gif" alt="저자문의" /></a>
				</dt>
				<dd>
					<ul>
						<li><a href="#">저자문의 관련된 내용입니다.</a> <span>2014-03-01</span></li>
						<li><a href="#">저자문의 관련된 내용입니다.</a> <span>2014-03-01</span></li>
						<li><a href="#">저자문의 관련된 내용입니다.</a> <span>2014-03-01</span></li>
						<li><a href="#">저자문의 관련된 내용입니다.</a> <span>2014-03-01</span></li>
						<li><a href="#">저자문의 관련된 내용입니다.</a> <span>2014-03-01</span></li>
					</ul>
					<p>
						<a href="#"><img src="images/tab_more_btn.gif" alt="더보기" /></a>
					</p>
				</dd>
			</dl>
			<ul id="consult_wrap">
				<li><img src="images/banner_consult.gif"
					alt="출판 상담 문의 02-3235-1722" /></li>
				<li><img src="images/banner_support.gif"
					alt="원고 양식 다운받기 및 지원 easy@easypub.co.kr" usemap="#support" /> <map
						name="support" id="support">
						<area shape="rect" coords="9,29,126,53" href="#" alt="원고 양식 다운 받기" />
					</map></li>
			</ul>
		</div>
		<div id="bestbook_zone banner_link">
			<h3>광고 협력업체</h3>
			<div id="best_bg">
				<ul>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>쿠팡</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>티몬</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>11번가</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>g마켓</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>위메프</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>쿠차</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>옥션</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>네이버</strong>광고업체</span></a></li>
					<li><a href="#"><img src="images/bannerlink.png" alt="" />
							<span><strong>다음</strong>광고업체</span> </a></li>
					<li><a href="#"> <img src="images/bannerlink.png" alt="" />
							<span><strong>구글</strong>광고업체</span></a></li>
				</ul>
				<!-- <p class="prev_btn">
					<a href="#"> <img src="images/bestbook_btn_left.png"
						alt="이전으로 이동" />
					</a>
				</p>
				<p class="next_btn">
					<a href="#"> <img src="images/bestbook_btn_right.png"
						alt="다음으로 이동" />
					</a>
				</p> -->
			</div>
		</div>
	</div>
</div>


