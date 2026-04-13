LOAD DATA
 APPEND INTO TABLE HNT_エリアＳＫＵ
 (
 納品エリアコード                         POSITION(1:6) CHAR,
 商品コード                               POSITION(7:19) CHAR,
 有効終了日                               POSITION(20:27) DATE "YYYYMMDD",
 有効開始日                               POSITION(28:35) DATE "YYYYMMDD",
 社コード                                 POSITION(36:41) CHAR,
 ベンダーコード                           POSITION(42:47) CHAR,
 ＤＥＰＴコード                           POSITION(48:50) CHAR,
 ＣＬＡＳＳコード                         POSITION(51:53) CHAR,
 配送センターベンダーコード               POSITION(54:59) CHAR,
 商品部地域コード                         POSITION(60:62) CHAR,
 ＳＣ発注終了日                           POSITION(63:70) DATE "YYYYMMDD",
 ＳＣ更新区分                             POSITION(71:72) CHAR,
 処理区分                                 POSITION(73:74) CHAR,
 作成日時                                 POSITION(75:88) DATE "YYYYMMDDHH24MISS",
 オンライン更新日時                       POSITION(89:102) DATE "YYYYMMDDHH24MISS",
 バッチ更新日時                           POSITION(103:116) DATE "YYYYMMDDHH24MISS",
 削除区分                                 POSITION(117:118) CHAR
 )
