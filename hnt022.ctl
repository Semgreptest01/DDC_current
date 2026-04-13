LOAD DATA
 APPEND INTO TABLE HNT_商品ＤＩＶ
 (
 社コード                                 POSITION(1:6) CHAR,
 商品ＤＩＶコード                         POSITION(7:9) CHAR,
 有効終了日                               POSITION(10:17) DATE "YYYYMMDD",
 有効開始日                               POSITION(18:25) DATE "YYYYMMDD",
 商品ＤＩＶ名                             POSITION(26:75) CHAR
  "DECODE(RTRIM(RTRIM(:商品ＤＩＶ名),'　'),NULL,'　',RTRIM(RTRIM(:商品ＤＩＶ名),'　'))",
 商品ＤＩＶ名カナ                         POSITION(76:100) CHAR
  "DECODE(RTRIM(RTRIM(:商品ＤＩＶ名カナ),'　'),NULL,'　',RTRIM(RTRIM(:商品ＤＩＶ名カナ),'　'))",
 入力依頼者氏名コード                     POSITION(101:108) CHAR,
 入力依頼者氏名                           POSITION(109:128) CHAR,
 処理区分                                 POSITION(129:130) CHAR,
 コピー処理日                             POSITION(131:138) DATE "YYYYMMDD",
 コピー処理状況区分                       POSITION(139:140) CHAR,
 作成者氏名コード                         POSITION(141:148) CHAR,
 作成者氏名                               POSITION(149:178) CHAR,
 作成者部署コード                         POSITION(179:184) CHAR,
 作成者部署名                             POSITION(185:224) CHAR,
 作成プログラムＩＤ                       POSITION(225:264) CHAR,
 作成日時                                 POSITION(265:278) DATE "YYYYMMDDHH24MISS",
 オンライン更新者氏名コード               POSITION(279:286) CHAR,
 オンライン更新者氏名                     POSITION(287:316) CHAR,
 オンライン更新者部署コード               POSITION(317:322) CHAR,
 オンライン更新者部署名                   POSITION(323:362) CHAR,
 オンライン更新プログラムＩＤ             POSITION(363:402) CHAR,
 オンライン更新日時                       POSITION(403:416) DATE "YYYYMMDDHH24MISS",
 バッチ更新プログラムＩＤ                 POSITION(417:456) CHAR,
 バッチ更新日時                           POSITION(457:470) DATE "YYYYMMDDHH24MISS",
 ロック連番                               POSITION(471:474) ZONED NULLIF ロック連番=BLANKS,
 削除区分                                 POSITION(475:476) CHAR,
 更新端末ＩＤ                             POSITION(477:506) CHAR
 )
