UPDATE ASTK_AD_INFO
SET AD_IMG_ADDR = '117.52.31.200:12249/ad/default.png'
WHERE ASTK_CHK='F';
commit

select * from astk_ad_info