 SELECT AD_CODE, COUNT(AD_CODE) AS A_CNT, HIST_TYPE, COUNT(HIST_TYPE) AS H_CNT FROM ASTK_AD_HISTORY
		GROUP BY AD_CODE, HIST_TYPE