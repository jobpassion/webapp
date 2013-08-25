-- phpMyAdmin SQL Dump
-- version 3.5.7
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 08 月 25 日 04:50
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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=88 ;

--
-- 转存表中的数据 `notes`
--

INSERT INTO `notes` (`id`, `user_id`, `title`, `desc`, `url`, `list`, `tag`, `updated`, `created`) VALUES
(82, 'unknownUser0.13105736975558102', 'jiajinping@genscript.comcvl8k9', '<div>jiajinping@genscript.com</div><div>cvl8k9</div><div><br></div><div>&nbsp; &nbsp;https://192.168.1.94/scm/login.action</div><div>&nbsp;</div><div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;admin</div><div>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;scmadmin</div><div><br></div><div><br></div><div><br></div><div>设计环境</div><div>http://10.168.2.5/scm/</div><div>SCM</div><div>genscript</div><div>&nbsp;</div><div>svn1:(开发库)</div><div>http://10.168.2.125/Development/SCM_DEV/trunk/src/main</div><div>Hailong</div><div>Hailong@qw#r</div><div>shihailong</div><div>&nbsp;</div><div>svn checkout http://10.168.2.125/Development/SCM_DEV/trunk/src/scm_optimization</div><div>&nbsp;</div><div>&nbsp;</div><div>svn2:(测试)</div><div>http://192.168.1.28/QA/SCM_QA</div><div>shihailong/shihailong</div><div>对应的scm环境https://192.168.1.94/scm</div><div>&nbsp;</div><div>&nbsp;</div><div>svn3:(正式)</div><div>http://192.168.1.28/Production/SCM_PROD</div><div>shihailong/shihailong</div><div>对应的scm环境http://scm.genscript.com/scm</div><div><br></div><div><br></div><div><br></div><div>maven repo:http://192.168.1.30:8081/nexus/index.html</div><div><br></div><div><br></div><div>正式环境路径</div><div>http://192.168.1.28/Production/SCM_PROD</div><div><br></div><div>测试环境路径</div><div>http://192.168.1.28/QA/SCM_QA</div><div><br></div><div><br></div><div><br></div><div><br></div><div><br></div><div>cd /opt/project/SCM</div><div>svn update ………………………………………….</div><div>&nbsp;</div><div>mvn clean</div><div>mvn</div><div>&nbsp;</div><div>service jboss stop</div><div>cp /opt/project/SCM/target/scm.war /usr/local/jboss/server/default/deploy/</div><div>service jboss start</div><div>&nbsp;</div><div>&nbsp;</div><div>tail -f /var/log/jboss.log</div><div><br></div><div><br></div><div><br></div><div><br></div><div>product env:</div><div>http://192.168.1.130:8080/scm/login.action</div><div>http://192.168.1.131:8080/scm/login.action</div><div>scm/scm</div><div>showlog</div><div><br></div><div><br></div><div>http://localhost:9080/scm/china_shipping!printMailList.action?pkgNo=105&amp;express=zt</div><div><br></div><div><br></div><div><br></div><div>http://192.168.1.91/phpmyadmin/</div><div>用户名：scm</div><div>密码：WauT7a</div><div><br></div><div><br></div><div><br></div><div>china ordering测试</div><div>http://10.168.2.125/Preproduction/china_ordering</div><div>http:/10.168.2.42:8080/scm</div><div>QA环境数据库地址为10.168.2.8, 端口3306</div><div>用户名scm, 密码Lk*7Gn0</div><div><br></div><div><br></div><div>正则:生成Select,&nbsp;</div><div>0.</div><div>([^,]*),&nbsp;</div><div>\\1 as \\1,&nbsp;</div><div>1.(do更换)</div><div>([^ ]* as)</div><div>do.\\1</div><div>2.多次</div><div>as ([^_ ]*)_([^_,])([^_,]*)</div><div>as \\1\\U\\2\\E\\3</div><div><br></div><div><br></div><div>劳动争议仲裁委员会</div><div>23min</div><div><br></div><div><br></div><div>jelastic:</div><div><span class="Apple-tab-span">	</span>maven:</div><div><span class="Apple-tab-span">	</span>https://app.jelastic.dogado.eu/JElastic/env/build/rest/addproject</div><div><span class="Apple-tab-span">		</span>appid:2551d2098a757dc6fcc2be8d114c4bd2</div><div><span class="Apple-tab-span">		</span>session:6397x5d83b3dc8a9a655b0b7d391f709b176b</div><div><span class="Apple-tab-span">		</span>type:git</div><div><span class="Apple-tab-span">		</span>path:https://github.com/jobpassion/webapp.git</div><div><span class="Apple-tab-span">		</span>branch:master</div><div><span class="Apple-tab-span">		</span>login:jobpassion</div><div><span class="Apple-tab-span">		</span>password:a261103692</div><div><span class="Apple-tab-span">		</span>name:webapp</div><div><span class="Apple-tab-span">		</span>env:redrum2</div><div><span class="Apple-tab-span">		</span>nodeid:89139</div><div><span class="Apple-tab-span">		</span>context:ROOT</div><div><span class="Apple-tab-span">		</span>charset:UTF-8</div><div><span class="Apple-tab-span">		</span>hx_lang:en</div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">	</span>build:</div><div><span class="Apple-tab-span">		</span>https://app.jelastic.dogado.eu/JElastic/env/build/rest/builddeployproject?_dc=1376987714249&amp;appid=2551d2098a757dc6fcc2be8d114c4bd2&amp;session=6397x5d83b3dc8a9a655b0b7d391f709b176b&amp;nodeid=89139&amp;projectid=2&amp;actionkey=build%3B2551d2098a757dc6fcc2be8d114c4bd2%3B89139%3B2&amp;charset=UTF-8&amp;hx_lang=en</div><div><span class="Apple-tab-span">		</span>_dc:1376987714249</div><div><span class="Apple-tab-span">		</span>appid:2551d2098a757dc6fcc2be8d114c4bd2</div><div><span class="Apple-tab-span">		</span>session:6397x5d83b3dc8a9a655b0b7d391f709b176b</div><div><span class="Apple-tab-span">		</span>nodeid:89139</div><div><span class="Apple-tab-span">		</span>projectid:2</div><div><span class="Apple-tab-span">		</span>actionkey:build;2551d2098a757dc6fcc2be8d114c4bd2;89139;2</div><div><span class="Apple-tab-span">		</span>charset:UTF-8</div><div><span class="Apple-tab-span">		</span>hx_lang:en</div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div><span class="Apple-tab-span">		</span></div><div>List&lt;Criterion&gt; criterionList</div><div><br></div><div><span class="Apple-tab-span">				</span>if (!filter.isMultiProperty()) {</div><div><span class="Apple-tab-span">					</span>Criterion criterion = buildPropertyFilterCriterion(filter</div><div><span class="Apple-tab-span">							</span>.getPropertyName(), filter.getPropertyValue(),</div><div><span class="Apple-tab-span">							</span>filter.getPropertyType(), filter.getMatchType());</div><div><span class="Apple-tab-span">					</span>criterionList.add(criterion);</div><div><span class="Apple-tab-span">				</span>} else {</div><div><span class="Apple-tab-span">					</span>Disjunction disjunction = Restrictions.disjunction();</div><div><span class="Apple-tab-span">					</span>for (String param : filter.getPropertyNames()) {</div><div><span class="Apple-tab-span">						</span>Criterion criterion = buildPropertyFilterCriterion(</div><div><span class="Apple-tab-span">								</span>param, filter.getPropertyValue(), filter</div><div><span class="Apple-tab-span">										</span>.getPropertyType(), filter</div><div><span class="Apple-tab-span">										</span>.getMatchType());</div><div><span class="Apple-tab-span">						</span>disjunction.add(criterion);</div><div><span class="Apple-tab-span">					</span>}</div><div><span class="Apple-tab-span">					</span>criterionList.add(disjunction);</div><div><span class="Apple-tab-span">				</span>}</div>', '', '', '', '2013-08-22 07:37:08', '2013-08-22 07:37:08'),
(86, 'unknownUser0.6225306442938745', 'f', 'fffff', '', '', '', '2013-08-22 11:36:52', '2013-08-22 11:36:52'),
(87, 'unknownUser0.32804686948657036', '', '', '', '', '', '2013-08-23 02:14:37', '2013-08-23 02:14:37');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) DEFAULT NULL,
  `user_id` varchar(64) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `user_name`, `user_id`, `password`, `created`) VALUES
(8, NULL, 'unknownUser0.13105736975558102', NULL, '2013-08-22 07:36:50'),
(9, NULL, 'unknownUser0.6225306442938745', NULL, '2013-08-22 11:36:40');
--
-- 数据库: `webapp`
--
CREATE DATABASE `webapp` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `webapp`;

-- --------------------------------------------------------

--
-- 表的结构 `test`
--

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
