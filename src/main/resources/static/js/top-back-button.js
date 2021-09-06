'use strict';
$(function () {
	
	// ボタンの表示／非表示を切り替える関数
	const updateButton = () => {
	  if ($(window).scrollTop() >= 50) {
	    // 50px以上スクロールされた
	    // ボタンを表示
	    $('.back-to-top').fadeIn();
	  } else {
	    // ボタンを非表示
	    $('.back-to-top').fadeOut();
	  }
	};

	// スクロールされる度にupdateButtonを実行
	$(window).on('scroll', updateButton);

	// ボタンをクリックしたらページトップにスクロールする
	$('.back-to-top').on('click', (e) => {
	  e.preventDefault();

	  // 600ミリ秒かけてトップに戻る
	  $('html, body').animate({ scrollTop: 0 }, 600);
	});

	// ページの途中でリロード（再読み込み）された場合でも、ボタンが表示されるようにする
	updateButton();

});