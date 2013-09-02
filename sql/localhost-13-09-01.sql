-- phpMyAdmin SQL Dump
-- version 3.5.7
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 09 月 01 日 09:54
-- 服务器版本: 10.0.1-MariaDB
-- PHP 版本: 5.4.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `chrome_note`
--
CREATE DATABASE `chrome_note` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `chrome_note`;

-- --------------------------------------------------------

--
-- 表的结构 `notes`
--

DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `desc` text,
  `url` varchar(500) DEFAULT NULL,
  `list` text,
  `tag` varchar(50) DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=106 ;

--
-- 转存表中的数据 `notes`
--

INSERT INTO `notes` (`id`, `user_id`, `title`, `desc`, `url`, `list`, `tag`, `updated`, `created`) VALUES
(98, 'unknownUser0.3220257544890046', 'jiajinping@genscript.comcvl8k9', '<div>jiajinping@genscript.com</div><div>cvl8k9</div><div><br></div><div>&nbsp; &nbsp;https://192.168.1.94/scm/login.action</div><div>&nbsp;</div><div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;admin</div><div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;scmadmin</div><div><br></div><div><br></div><div><br></div><div>设计环境</div><div>http://10.168.2.5/scm/</div><div>SCM</div><div>genscript</div><div>&nbsp;</div><div>svn1:(开发库)</div><div>http://10.168.2.125/Development/SCM_DEV/trunk/src/main</div><div>Hailong</div><div>Hailong@qw#r</div><div>shihailong</div><div>&nbsp;</div><div>svn checkout http://10.168.2.125/Development/SCM_DEV/trunk/src/scm_optimization</div><div>&nbsp;</div><div>&nbsp;</div><div>svn2:(测试)</div><div>http://192.168.1.28/QA/SCM_QA</div><div>shihailong/shihailong</div><div>对应的scm环境https://192.168.1.94/scm</div><div>&nbsp;</div><div>&nbsp;</div><div>svn3:(正式)</div><div>http://192.168.1.28/Production/SCM_PROD</div><div>shihailong/shihailong</div><div>对应的scm环境http://scm.genscript.com/scm</div><div><br></div><div><br></div><div><br></div><div>maven repo:http://192.168.1.30:8081/nexus/index.html</div><div><br></div><div><br></div><div>正式环境路径</div><div>http://192.168.1.28/Production/SCM_PROD</div><div><br></div><div>测试环境路径</div><div>http://192.168.1.28/QA/SCM_QA</div><div><br></div><div><br></div><div><br></div><div><br></div><div><br></div><div>cd /opt/project/SCM</div><div>svn update ………………………………………….</div><div>&nbsp;</div><div>mvn clean</div><div>mvn</div><div>&nbsp;</div><div>service jboss stop</div><div>cp /opt/project/SCM/target/scm.war /usr/local/jboss/server/default/deploy/</div><div>service jboss start</div><div>&nbsp;</div><div>&nbsp;</div><div>tail -f /var/log/jboss.log</div><div><br></div><div><br></div><div><br></div><div><br></div><div>product env:</div><div>http://192.168.1.130:8080/scm/login.action</div><div>http://192.168.1.131:8080/scm/login.action</div><div>scm/scm</div><div>showlog</div><div><br></div><div><br></div><div>http://localhost:9080/scm/china_shipping!printMailList.action?pkgNo=105&amp;express=zt</div><div><br></div><div><br></div><div><br></div><div>http://192.168.1.91/phpmyadmin/</div><div>用户名：scm</div><div>密码：WauT7a</div><div><br></div><div><br></div><div><br></div><div>china ordering测试</div><div>http://10.168.2.125/Preproduction/china_ordering</div><div>http:/10.168.2.42:8080/scm</div><div>QA环境数据库地址为10.168.2.8, 端口3306</div><div>用户名scm, 密码Lk*7Gn0</div><div><br></div><div><br></div><div>正则:生成Select,&nbsp;</div><div>0.</div><div>([^,]*),&nbsp;</div><div>\\1 as \\1,&nbsp;</div><div>1.(do更换)</div><div>([^ ]* as)</div><div>do.\\1</div><div>2.多次</div><div>as ([^_ ]*)_([^_,])([^_,]*)</div><div>as \\1\\U\\2\\E\\3</div><div><br></div><div><br></div><div>劳动争议仲裁委员会</div><div>23min</div><div><br></div><div><br></div><div>jelastic:</div><div><span class="Apple-tab-span">	</span>maven:</div><div><span class="Apple-tab-span">	</span>https://app.jelastic.dogado.eu/JElastic/env/build/rest/addproject</div><div><span class="Apple-tab-span">		</span>appid:2551d2098a757dc6fcc2be8d114c4bd2</div><div><span class="Apple-tab-span">		</span>session:6397x5d83b3dc8a9a655b0b7d391f709b176b</div><div><span class="Apple-tab-span">		</span>type:git</div><div><span class="Apple-tab-span">		</span>path:https://github.com/jobpassion/webapp.git</div><div><span class="Apple-tab-span">		</span>branch:master</div><div><span class="Apple-tab-span">		</span>login:jobpassion</div><div><span class="Apple-tab-span">		</span>password:a261103692</div><div><span class="Apple-tab-span">		</span>name:webapp</div><div><span class="Apple-tab-span">		</span>env:redrum2</div><div><span class="Apple-tab-span">		</span>nodeid:89139</div><div><span class="Apple-tab-span">		</span>context:ROOT</div><div><span class="Apple-tab-span">		</span>charset:UTF-8</div><div><span class="Apple-tab-span">		</span>hx_lang:en</div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">	</span>build:</div><div><span class="Apple-tab-span">		</span>https://app.jelastic.dogado.eu/JElastic/env/build/rest/builddeployproject?_dc=1376987714249&amp;appid=2551d2098a757dc6fcc2be8d114c4bd2&amp;session=6397x5d83b3dc8a9a655b0b7d391f709b176b&amp;nodeid=89139&amp;projectid=2&amp;actionkey=build%3B2551d2098a757dc6fcc2be8d114c4bd2%3B89139%3B2&amp;charset=UTF-8&amp;hx_lang=en</div><div><span class="Apple-tab-span">		</span>_dc:1376987714249</div><div><span class="Apple-tab-span">		</span>appid:2551d2098a757dc6fcc2be8d114c4bd2</div><div><span class="Apple-tab-span">		</span>session:6397x5d83b3dc8a9a655b0b7d391f709b176b</div><div><span class="Apple-tab-span">		</span>nodeid:89139</div><div><span class="Apple-tab-span">		</span>projectid:2</div><div><span class="Apple-tab-span">		</span>actionkey:build;2551d2098a757dc6fcc2be8d114c4bd2;89139;2</div><div><span class="Apple-tab-span">		</span>charset:UTF-8</div><div><span class="Apple-tab-span">		</span>hx_lang:en</div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div>List&lt;Criterion&gt; criterionList</div><div><br></div><div><span class="Apple-tab-span">				</span>if (!filter.isMultiProperty()) {</div><div><span class="Apple-tab-span">					</span>Criterion criterion = buildPropertyFilterCriterion(filter</div><div><span class="Apple-tab-span">							</span>.getPropertyName(), filter.getPropertyValue(),</div><div><span class="Apple-tab-span">							</span>filter.getPropertyType(), filter.getMatchType());</div><div><span class="Apple-tab-span">					</span>criterionList.add(criterion);</div><div><span class="Apple-tab-span">				</span>} else {</div><div><span class="Apple-tab-span">					</span>Disjunction disjunction = Restrictions.disjunction();</div><div><span class="Apple-tab-span">					</span>for (String param : filter.getPropertyNames()) {</div><div><span class="Apple-tab-span">						</span>Criterion criterion = buildPropertyFilterCriterion(</div><div><span class="Apple-tab-span">								</span>param, filter.getPropertyValue(), filter</div><div><span class="Apple-tab-span">										</span>.getPropertyType(), filter</div><div><span class="Apple-tab-span">										</span>.getMatchType());</div><div><span class="Apple-tab-span">						</span>disjunction.add(criterion);</div><div><span class="Apple-tab-span">					</span>}</div><div><span class="Apple-tab-span">					</span>criterionList.add(disjunction);</div><div><span class="Apple-tab-span">				</span>}</div><div><br></div>其实没一定的。我以前走的时候没有拿这个证明但是去有的大企业的时候就没问我要这个..之后去年去一个日企的时候问我要这个的。.要我去当地居委会开证明.很简单的。没离职证明就去开一个待业证明!!!居委会盖个章就可以了', '', '', '', '2013-08-25 12:32:39', '2013-08-25 12:32:39'),
(101, 'unknownUser0.9196849865838885', '这是我本地的存储', '这是我本地的存储', '', '', '', '2013-08-26 12:52:42', '2013-08-26 12:52:42'),
(104, 'unknownUser0.5123053207062185', '一封留言', '现', '', '', '', '2013-08-27 01:55:16', '2013-08-27 01:55:16');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `user_name`, `user_id`, `password`, `created`) VALUES
(11, NULL, 'unknownUser0.3220257544890046', NULL, '2013-08-25 12:09:42'),
(12, NULL, 'unknownUser0.03691133437678218', NULL, '2013-08-26 12:08:19'),
(13, NULL, 'unknownUser0.9196849865838885', NULL, '2013-08-26 12:52:16'),
(14, NULL, 'unknownUser0.5123053207062185', NULL, '2013-08-27 01:54:27');
--
-- 数据库: `webapp`
--
CREATE DATABASE `webapp` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `webapp`;

-- --------------------------------------------------------

--
-- 表的结构 `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE IF NOT EXISTS `chat_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fromId` varchar(32) DEFAULT NULL,
  `toId` varchar(32) DEFAULT NULL,
  `message` text,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=248 ;

--
-- 转存表中的数据 `chat_message`
--

INSERT INTO `chat_message` (`id`, `fromId`, `toId`, `message`, `status`) VALUES
(2, 'a', 'b', 'def', 'old'),
(3, 'a', 'b', 'd', 'old'),
(4, 'a', 'b', 'dddd', 'old'),
(5, 'A', 'B', '在？\n', 'new'),
(6, 'A', 'B', '在？\n', 'new'),
(7, 'A', 'B', '在？\n', 'new'),
(8, 'A', 'B', '在？\n', 'new'),
(9, 'A', 'B', '在？\n', 'new'),
(10, '我', '贾', '恩', 'new'),
(11, 'hua', 'jia', '恩', 'old'),
(12, 'jia', 'hua', '能看到吗', 'old'),
(13, 'hua', 'jia', '恩', 'old'),
(14, 'jia', 'hua', '收到了', 'old'),
(15, 'jia', 'hua', '点一次发送就行', 'old'),
(16, 'jia', 'hua', '???', 'old'),
(17, 'jia', 'hua', '能看到吗', 'old'),
(18, 'hua', 'jia', '那不能显示聊天记录呀', 'old'),
(19, 'jia', 'hua', '悲剧了', 'old'),
(20, 'hua', 'jia', '我等看到\n', 'old'),
(21, 'jia', 'hua', '只能看到对方的聊天记录...', 'old'),
(22, 'jia', 'hua', '我收到你的了', 'old'),
(23, 'hua', 'jia', '还敢说没BUG\n知道了\n', 'old'),
(24, 'jia', 'hua', '恩\n恩\n那不能显示聊天记录呀\n我等看到', 'old'),
(25, 'jia', 'hua', '这不算BUG吧...只是我做太急了', 'old'),
(26, 'jia', 'hua', '好没感觉啊,这样聊', 'old'),
(27, 'hua', 'jia', '是啊', 'old'),
(28, 'jia', 'hua', '可以点击发送以后,自动清空输入栏,然后同时把自己发的送到屏上去', 'old'),
(29, 'hua', 'jia', '费死尽了', 'old'),
(30, 'hua', 'jia', '用户体验不好', 'old'),
(31, 'jia', 'hua', '这个有新消息提示呀', 'old'),
(32, 'jia', 'hua', '不信息你最小化这个窗口试下', 'old'),
(33, 'hua', 'jia', '在哪了？', 'old'),
(34, 'jia', 'hua', '有新消息,如果当前窗口不是处于激活的话,图标会闪的', 'old'),
(35, 'hua', 'jia', '看到了', 'old'),
(36, 'hua', 'jia', '就是没有聊天记录比较抑郁', 'old'),
(37, 'jia', 'hua', '加一下只需要几分钟', 'old'),
(38, 'hua', 'jia', '那你加一下呀', 'old'),
(39, 'jia', 'hua', '等你下班了我优化一下吧...现在时间都不多了,我再花时间改就不划算了', 'old'),
(40, 'jia', 'hua', '行,我现在改下,可以边说边改', 'old'),
(41, 'hua', 'jia', '恩 好的', 'old'),
(42, 'jia', 'hua', '今天下午丢你一个人半天,会不会好无聊呀?\n', 'old'),
(43, 'hua', 'jia', '这个发送是什么快捷键？', 'old'),
(44, 'jia', 'hua', '没有快捷键....好吧,我加一下\n', 'old'),
(45, 'hua', 'jia', '你要改的好用一些呀', 'old'),
(46, 'hua', 'jia', '不可以比QQ差', 'old'),
(47, 'jia', 'hua', '好的', 'old'),
(48, 'jia', 'hua', '之前的是快速开发版', 'old'),
(49, 'jia', 'hua', '如果我可以持续做好的话,你会和我一直用这个吗', 'old'),
(50, 'hua', 'jia', '可以呀', 'old'),
(51, 'hua', 'jia', '你要是语音啊什么的都有  多爽呀', 'old'),
(52, 'jia', 'hua', '只有我们两个人用的聊天工具', 'old'),
(53, 'hua', 'jia', '恩', 'old'),
(54, 'jia', 'hua', '语音可以加的', 'old'),
(55, 'hua', 'jia', '那好啊  晚上可以再细聊一下其他功能', 'old'),
(56, 'jia', 'hua', '没想到做一个小时的东西,你这么支持我', 'old'),
(57, 'hua', 'jia', '哈哈  最好能有表情呀', 'old'),
(58, 'jia', 'hua', '这个需要你画一些出来才行', 'old'),
(59, 'hua', 'jia', '恩 可以的', 'old'),
(60, 'jia', 'hua', '我再改呢,还差一个快捷键的', 'old'),
(61, 'hua', 'jia', '恩  不急', 'old'),
(62, 'jia', 'hua', '那定回车键?', 'old'),
(63, 'jia', 'hua', '测试', 'old'),
(64, 'jia', 'hua', '测试一下新的', 'old'),
(65, 'hua', 'jia', 'CTRL+回车', 'old'),
(66, 'jia', 'hua', '测试一下新的', 'old'),
(67, 'jia', 'hua', '我测试一下新的哈', 'old'),
(68, 'a', 'b', 'fdas', 'new'),
(69, 'a', 'b', 'rewq', 'new'),
(70, 'a', 'b', 'fdsa', 'new'),
(71, 'a', 'b', 'fdsa', 'new'),
(72, 'hua', 'jia', '好的', 'old'),
(73, 'a', 'b', 'fdsafd', 'new'),
(74, 'a', 'b', 'rewqrqe', 'new'),
(75, 'jia', 'hua', '可以了,我发你吧', 'old'),
(76, 'hua', 'jia', '现在的就可以删了呗？', 'old'),
(77, 'jia', 'hua', '不用删,我发你,它会自己更新', 'old'),
(78, 'hua', 'jia', '好的', 'old'),
(79, 'jia', 'hua', '发你了', 'old'),
(80, 'hua', 'jia', '恩', 'old'),
(81, 'jia', 'hua', '我已经用新的了\n', 'old'),
(82, 'hua', 'jia', '换了', 'old'),
(83, 'hua', 'jia', '好了  哈哈', 'old'),
(84, 'jia', 'hua', '刚刚的小问题都解决了', 'old'),
(85, 'hua', 'jia', '真快呀', 'old'),
(86, 'hua', 'jia', '厉害呀  哈哈', 'old'),
(87, 'jia', 'hua', '测试一下它会不会自动滚到最下面\n', 'old'),
(88, 'hua', 'jia', '好', 'old'),
(89, 'jia', 'hua', '好像会,但很慢...\n', 'old'),
(90, 'hua', 'jia', '刚才不会  现在改完了？', 'old'),
(91, 'jia', 'hua', '是啊,现在改了', 'old'),
(92, 'jia', 'hua', '但是它是4秒后刷新\n', 'old'),
(93, 'jia', 'hua', '还行吧,先这样,至少不用手动去拉滚动条了\n', 'old'),
(94, 'hua', 'jia', '慢呀', 'old'),
(95, 'jia', 'hua', '是的,刚刚漏了一个地方,导致的', 'old'),
(96, 'hua', 'jia', '还真是   能再快点不？', 'old'),
(97, 'jia', 'hua', '可以马上的', 'old'),
(98, 'hua', 'jia', '再去改', 'old'),
(99, 'jia', 'hua', '姐姐你都快下班了...', 'old'),
(100, 'hua', 'jia', '是的', 'old'),
(101, 'jia', 'hua', '以后就不可以边聊天边刷微博了哎', 'old'),
(102, 'hua', 'jia', '是的呀  不错呀', 'old'),
(103, 'a', 'b', 'fdsa', 'new'),
(104, 'a', 'b', 'fdsa', 'new'),
(105, 'a', 'b', 'fdsaf', 'new'),
(106, 'a', 'b', 'das', 'new'),
(107, 'a', 'b', 'fda', 'new'),
(108, 'a', 'b', 'dsa', 'new'),
(109, 'a', 'b', 'ds', 'new'),
(110, 'a', 'b', 'dsa', 'new'),
(111, 'a', 'b', 'da', 'new'),
(112, 'a', 'b', 'fds', 'new'),
(113, 'a', 'b', 'fdsa', 'new'),
(114, 'a', 'b', 'fd', 'new'),
(115, 'a', 'b', 'fdsa', 'new'),
(116, 'a', 'b', 'fda', 'new'),
(117, 'a', 'b', 'fdsa', 'new'),
(118, 'a', 'b', 'rewq', 'new'),
(119, 'a', 'b', 'fds', 'new'),
(120, 'a', 'b', 'fdsa', 'new'),
(121, 'a', 'b', 'fdsa', 'new'),
(122, 'a', 'b', 'fdsa', 'new'),
(123, 'a', 'b', 'fdsa', 'new'),
(124, 'a', 'b', 'fdsa', 'new'),
(125, 'a', 'b', 'req', 'new'),
(126, 'a', 'b', 'fdas', 'new'),
(127, 'a', 'b', 'fdsa', 'new'),
(128, 'a', 'b', 'rewq', 'new'),
(129, 'a', 'b', 'fdsa', 'new'),
(130, 'a', 'b', 'fdsa', 'new'),
(131, 'a', 'b', 'fda', 'new'),
(132, 'a', 'b', 'fda', 'new'),
(133, 'a', 'b', 'fdas', 'new'),
(134, 'a', 'b', 'fdas', 'new'),
(135, 'jia', 'hua', '本来可以做这个免安装的', 'old'),
(136, 'jia', 'hua', '但是今天时间来不及了', 'old'),
(137, 'hua', 'jia', '晚上可以呀', 'old'),
(138, 'hua', 'jia', '我现在先不走', 'old'),
(139, 'jia', 'hua', '免安装的,有一部分比较棘手的代码,在我以前公司有,我现在没有留存', 'old'),
(140, 'jia', 'hua', '免安装的就是,不用安装air,我发布的包里全有了', 'old'),
(141, '', '', '又改了一次？', 'old'),
(142, 'jia', 'hua', '刚刚修了下显示屏滚动的,发你邮箱了\n', 'old'),
(143, '', '', '名字显示不出来呀？', 'old'),
(144, 'jia', 'hua', '前两天你下班太早了,今天补点班(偷笑)', 'old'),
(145, '', '', '这个不好吧', 'old'),
(146, '', '', '出BUG了', 'old'),
(147, 'jia', 'hua', '在?', 'old'),
(148, 'hua', 'jia', '是的', 'old'),
(149, 'hua', 'jia', '我忘了', 'old'),
(150, 'hua', 'jia', '不用设置就好了', 'old'),
(151, 'jia', 'hua', '不用设置不行的,但是可以设置一次,以后保存', 'old'),
(152, 'jia', 'hua', '你设置发送给我,我这设置的发送给你', 'old'),
(153, 'hua', 'jia', '什么意思？', 'old'),
(154, 'jia', 'hua', '就是这个应该由用户设置的,就好比你在QQ上登陆,就是设置了你的名字,你再点开我的头像,就是设置了对方名字\n', 'old'),
(155, 'hua', 'jia', '后来就相当于自动登录了呗', 'old'),
(156, 'jia', 'hua', '是的啊', 'old'),
(157, 'hua', 'jia', '来是4秒钟呀', 'old'),
(158, 'jia', 'hua', '是的,最长延迟4秒钟', 'old'),
(159, 'jia', 'hua', '可以做无延迟的,但..还是开发时间', 'old'),
(160, 'hua', 'jia', '恩   那就先用着吧', 'old'),
(161, 'jia', 'hua', '嗯,好', 'old'),
(162, 'jia', 'hua', '又快到周末了,你这周有休息不', 'old'),
(163, 'hua', 'jia', '这周先不休息', 'old'),
(164, 'jia', 'hua', '忙啊?', 'old'),
(165, 'hua', 'jia', '还行 有点 月末 月出都会忙的', 'old'),
(166, 'jia', 'hua', '那你没休息了,身体.....\n', 'old'),
(167, 'hua', 'jia', '没事了', 'old'),
(168, 'hua', 'jia', '攒着吧  反正现在回家也方便', 'old'),
(169, 'jia', 'hua', '这周末回家吗', 'old'),
(170, 'hua', 'jia', '不  我周一到周四之间吧', 'old'),
(171, 'jia', 'hua', '今晚我也吃麻辣烫去', 'old'),
(172, 'hua', 'jia', '恩', 'old'),
(173, 'hua', 'jia', '那几点能到家 8点多？', 'old'),
(174, 'jia', 'hua', '7点半', 'old'),
(175, 'jia', 'hua', '我买麻辣烫带回去', 'old'),
(176, 'hua', 'jia', '哦 这样啊', 'old'),
(177, 'jia', 'hua', '你早到家了先休息一下吧', 'old'),
(178, 'jia', 'hua', '这几天你很累了', 'old'),
(179, 'hua', 'jia', '恩 好的呀', 'old'),
(180, 'jia', 'hua', '一会儿我都要回了,你还没回', 'old'),
(181, 'hua', 'jia', '恩  今天 晚点', 'old'),
(182, 'jia', 'hua', '我一会儿先走了啊', 'old'),
(183, 'hua', 'jia', '好的', 'old'),
(184, 'jia', 'hua', '晚上吃饭别耽误', 'old'),
(185, 'hua', 'jia', '恩', 'old'),
(186, 'hua', 'jia', '在？', 'old'),
(187, 'jia', 'hua', '嗯', 'old'),
(188, 'hua', 'jia', '竟然在呀 哈哈', 'old'),
(189, 'hua', 'jia', '这个如果对方不在线的  话  会保留信息吗？', 'old'),
(190, 'jia', 'hua', '我还以为你肯定不会发的\n', 'old'),
(191, 'jia', 'hua', '会', 'old'),
(192, 'hua', 'jia', '我想发表情了', 'old'),
(193, 'hua', 'jia', '人呢？', 'old'),
(194, 'jia', 'hua', '等一下我啊\n', 'old'),
(195, 'hua', 'jia', '掉线了要能自动登录就好了\n', 'old'),
(196, 'jia', 'hua', '回来了', 'old'),
(197, 'hua', 'jia', '恩', 'old'),
(198, 'hua', 'jia', '忙吧  软件六日再弄吧', 'old'),
(199, 'jia', 'hua', '好的.这破项目真的很愁人', 'old'),
(200, 'hua', 'jia', '恩', 'old'),
(201, 'hua', 'jia', '来！签个到', 'old'),
(202, 'jia', 'hua', '好啊', 'old'),
(203, 'jia', 'hua', '"到"!\n', 'old'),
(204, 'jia', 'hua', '你到了没啊', 'old'),
(205, 'hua', 'jia', '嘿嘿！', 'old'),
(206, 'hua', 'jia', '上班了呀', 'old'),
(207, 'jia', 'hua', '那你要打"到"啊', 'old'),
(208, 'hua', 'jia', '到\n', 'old'),
(209, 'jia', 'hua', '=_=\n', 'old'),
(210, 'jia', 'hua', '  ', 'old'),
(211, 'hua', 'jia', '怎么了', 'old'),
(212, 'jia', 'hua', '发表情没发成功..', 'old'),
(213, 'jia', 'hua', 'o(-"-)o', 'old'),
(214, 'jia', 'hua', '成功了', 'old'),
(215, 'hua', 'jia', '害的手动', 'old'),
(216, 'jia', 'hua', '嘿..', 'old'),
(217, 'hua', 'jia', '开工吧', 'old'),
(218, 'jia', 'hua', '好的', 'old'),
(219, 'jia', 'hua', '还有在么', 'old'),
(220, 'jia', 'jia', 'aaaaaaaaaaaaaaaaa', 'old'),
(221, 'jia', 'jia', '111111111111111111', 'old'),
(222, 'jia', 'hua', '1111111111111', 'old'),
(223, 'hua', 'hua', 'gggg', 'old'),
(224, 'jia', 'hua', '可以了吗', 'old'),
(225, 'hua', 'jia', 'gjkjkjjjjjkj', 'old'),
(226, 'jia', 'hua', '1111111111111111111111111111111111111111111111111111', 'old'),
(227, 'jia', 'hua', '用它聊天？', 'old'),
(228, 'jia', 'hua', 'aaaa', 'old'),
(229, 'jia', 'hua', '给我发个消息', 'old'),
(230, 'hua', 'jia', 'lllk', 'old'),
(231, 'jia', 'hua', 'aaa', 'old'),
(232, 'hua', 'jia', 'fhfhf', 'old'),
(233, 'hua', 'jia', 'nnnn', 'old'),
(234, 'jia', 'hua', 'aaaa', 'old'),
(235, 'jia', 'jia', 'aaa\n', 'old'),
(236, 'jia', 'jia', 'aaaa', 'old'),
(237, 'jia', 'jia111', 'dddd', 'new'),
(238, 'jia', 'jia1', 'aaa', 'new'),
(239, 'jia', 'jia1', '1111', 'new'),
(240, 'jia', 'jia1', 'aaaa', 'new'),
(241, 'a', 'b', 'aaaa', 'new'),
(242, 'a', 'a', 'fdsafdafdasfdasfda', 'old'),
(243, 'a', 'b', 'aaaaaaaaaaaaaaa\n\n', 'new'),
(244, 'a', 'b', 'fdsafdsafafa', 'new'),
(245, 'a', 'b', 'aaaaaaaa', 'old'),
(246, 'a', 'b', '111111111111111', 'old'),
(247, 'a', 'b', 'aaaaaaaaaaaaaa', 'new');

-- --------------------------------------------------------

--
-- 表的结构 `test`
--

DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `test`
--

INSERT INTO `test` (`id`, `name`) VALUES
(1, 'fdafd'),
(2, 'dfefe');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
