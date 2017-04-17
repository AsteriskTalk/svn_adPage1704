
CREATE TABLE ASTK_CLIENT_INFO(
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
CLIENT_ID NVARCHAR2(100) NOT NULL,
CLIENT_PW NVARCHAR2(100) NOT NULL,
IS_CONN CHAR(1) DEFAULT 'N' NOT NULL,
CONSTRAINT PK_CLIENT_INFO_CCODE PRIMARY KEY (CLIENT_CODE),
CONSTRAINT UNQ_CLIENT_INFO_CID UNIQUE (CLIENT_ID)
);

CREATE TABLE ASTK_CLIENT_PROFILE(
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
CLIENT_EMAIL NVARCHAR2(100) NOT NULL,
CLIENT_PHONE NVARCHAR2(100) NOT NULL,
CLIENT_NAME NVARCHAR2(100) NOT NULL,
CLIENT_CTT NVARCHAR2(999) DEFAULT 'NOPE' NOT NULL,
CLIENT_NUM NVARCHAR2(100) ,
CLIENT_LOGO_ADDR NVARCHAR2(999) DEFAULT 'NOPE' NOT NULL,
CONSTRAINT FK_CLIENT_PROFILE_CCODE FOREIGN KEY (CLIENT_CODE)
	REFERENCES ASTK_CLIENT_INFO(CLIENT_CODE)
);

CREATE TABLE ASTK_CLIENT_POINT(
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
SUM_PUR_PRICE NUMBER DEFAULT 0 NOT NULL,
SUM_PUR_POINT NUMBER DEFAULT 0 NOT NULL,
SUM_PB_PRICE NUMBER DEFAULT 0	NOT NULL,
SUM_PB_POINT NUMBER DEFAULT 0 NOT NULL,
NOW_POINT NUMBER DEFAULT 0 NOT NULL,
CONSTRAINT FK_CLIENT_POINT_CCODE FOREIGN KEY (CLIENT_CODE)
	REFERENCES ASTK_CLIENT_INFO(CLIENT_CODE)
);

CREATE TABLE ASTK_OTP_QUERY(
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
OTP NUMBER NOT NULL,
SQL NVARCHAR2(999) NOT NULL,
IS_USED CHAR(1) DEFAULT 'T' NOT NULL,
CREATE_DATE NUMBER DEFAULT 0 NOT NULL,
CONSTRAINT PK_OTP_QUERY_OTP PRIMARY KEY (OTP)
);


CREATE TABLE ASTK_AD_INFO (
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
AD_CODE NUMBER NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
AD_CTT NVARCHAR2(999) NOT NULL,
AD_URL NVARCHAR2(999) DEFAULT 'NOPE' NOT NULL,
AD_IMG_ADDR NVARCHAR2(999) DEFAULT 'NOPE' NOT NULL,
AD_BONUS NUMBER DEFAULT 10 NOT NULL,
AD_VIEW NUMBER DEFAULT 10 NOT NULL,
AD_REMAIN_POINT NUMBER DEFAULT 0 NOT NULL,
AD_REMAIN_COUNT NUMBER DEFAULT 0 NOT NULL,
IS_ADING CHAR(1) DEFAULT 'T' NOT NULL,
IS_CONN CHAR(1) DEFAULT 'T' NOT NULL,
CONSTRAINT CPK_AD_INFO_ACODE_CCODE PRIMARY KEY (AD_CODE, CLIENT_CODE),
CONSTRAINT FK_AD_INFO_CCODE FOREIGN KEY (CLIENT_CODE)
	REFERENCES ASTK_CLIENT_INFO(CLIENT_CODE)
);

CREATE TABLE ASTK_AD_TARGET (
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
AD_CODE NUMBER NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
TARGET_TYPE CHAR(1) DEFAULT 'D' NOT NULL,
TARGET_VALUE NVARCHAR2(50) DEFAULT 'NOPE' NOT NULL,
CONSTRAINT CFK_AD_TARGET_ACODE_CCODE FOREIGN KEY(AD_CODE, CLIENT_CODE)
	REFERENCES ASTK_AD_INFO (AD_CODE, CLIENT_CODE)
);

CREATE TABLE ASTK_CLIENT_HISTORY (
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
AD_CODE NUMBER,
HIST_TYPE CHAR(1) DEFAULT 'D' NOT NULL,
HIST_DATE NUMBER NOT NULL,
HIST_POINT NUMBER NOT NULL,
CONSTRAINT FK_CLIENT_HIST_CCODE FOREIGN KEY(CLIENT_CODE)
	REFERENCES ASTK_CLIENT_INFO(CLIENT_CODE)
);

CREATE TABLE ASTK_AD_HISTORY (
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
AD_CODE NUMBER NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
USER_CODE NUMBER,
HIST_TYPE CHAR(1) DEFAULT 'D' NOT NULL,
HIST_DATE NUMBER NOT NULL,
HIST_POINT NUMBER DEFAULT 0 NOT NULL,
CONSTRAINT CFK_AD_HIST_ACODE_CCODE FOREIGN KEY(AD_CODE, CLIENT_CODE)
	REFERENCES ASTK_AD_INFO (AD_CODE, CLIENT_CODE)
);

CREATE TABLE ASTK_AD_SENDED (
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
SENDED_CODE NUMBER NOT NULL,
AD_CODE NUMBER NOT NULL,
CLIENT_CODE NUMBER NOT NULL,
USER_CODE NUMBER NOT NULL,
CHAT_GRP_CODE NUMBER NOT NULL,
AD_SEND_TIME NUMBER NOT NULL,
WAS_VIEW CHAR(1) DEFAULT 'F' NOT NULL,
WAS_TOUCH CHAR(1) DEFAULT 'F' NOT NULL,
CONSTRAINT PK_AD_SENDED_SCODE PRIMARY KEY(SENDED_CODE),
CONSTRAINT CFK_AD_SENDED_ACODE_CCODE FOREIGN KEY(AD_CODE, CLIENT_CODE)
	REFERENCES ASTK_AD_INFO (AD_CODE, CLIENT_CODE)
);

ALTER TABLE ASTK_AD_SENDED
RENAME COLUMN CHAR_GRP_CODE TO CHAT_GRP_CODE;

CREATE TABLE ASTK_OPENED_ROOM (
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
CHAT_GRP_CODE NUMBER NOT NULL,
USER_CODE NUMBER NOT NULL,
AD_SEND_TIME NUMBER DEFAULT '0' NOT NULL,
CONSTRAINT CPK_OPENDED_ROOM PRIMARY KEY(CHAT_GRP_CODE, USER_CODE)
);

commit

CREATE TABLE ASTK_USER_HISTORY (
ASTK_CHK CHAR(1) DEFAULT 'F' NOT NULL,
USER_CODE NUMBER NOT NULL,
CHAT_GRP_CODE NUMBER NOT NULL,
CLIENT_CODE NUMBER ,
AD_CODE NUMBER ,
SPEND_TYPE CHAR(2),
HIST_TYPE CHAR(1) NOT NULL,
HIST_DATE NUMBER NOT NULL,
HIST_POINT NUMBER NOT NULL,
CONSTRAINT CFK_USER_HIST_CCODE_ACODE FOREIGN KEY(CLIENT_CODE, AD_CODE)
	REFERENCES ASTK_AD_INFO(CLIENT_CODE, AD_CODE)
);
COMMIT

CREATE TABLE ASTK_OTC_INFO (
OTC NVARCHAR2(20) NOT NULL,
OTC_QUERY NVARCHAR2(999) NOT NULL,
PK_CODE NUMBER NOT NULL,
OTC_DATE NUMBER NOT NULL,
IS_USED CHAR(1) DEFAULT 'F' NOT NULL,
CONSTRAINT PK_OTC_OCODE PRIMARY KEY(OTC)
);
COMMIT


DROP TABLE ASTK_OTC_INFO;


select name, value$ from sys.props$
where name in ('NLS_CHARACTERSET', 'NLS_TERRITORY', 'NLS_LANGUAGE');

SELECT CLIENT_CODE, CLIENT_ID, IS_CONN FROM ASTK_CLIENT_INFO  WHERE CLIENT_ID='rkdwocks91'

SELECT * FROM ASTK_AD_INFO  WHERE AD_CODE=2000010001 AND CLIENT_CODE=1000010008


