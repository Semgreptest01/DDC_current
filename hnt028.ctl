LOAD DATA
 APPEND INTO TABLE HNT_入荷確定累積
 (
 ＤＤＣコード                             POSITION(1:6) CHAR,
 ベンダーコード                           POSITION(7:12) CHAR,
 商品コード                               POSITION(13:18) CHAR,
 処理日                                   POSITION(19:26) DATE "YYYYMMDD",
 締日時                                   POSITION(27:30) CHAR,
 入荷予定日                               POSITION(31:38) CHAR,
 入荷確定日                               POSITION(39:46) CHAR,
 入確区分                                 POSITION(47:47) CHAR,
 ＪＡＮコード                             POSITION(48:63) CHAR,
 発注日                                   POSITION(64:71) CHAR,
 入荷予定数                               POSITION(72:77) ZONED NULLIF 入荷予定数=BLANKS,
 入荷確定数                               POSITION(78:83) ZONED NULLIF 入荷確定数=BLANKS,
 ケース内入数                             POSITION(84:88) ZONED NULLIF ケース内入数=BLANKS,
 オーダーＮＯ                             POSITION(89:96) CHAR,
 メーカー伝票ＮＯ                         POSITION(97:104) CHAR,
 賞味製造日付                             POSITION(105:110) CHAR
 )
