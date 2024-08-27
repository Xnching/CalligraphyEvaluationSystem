use calligraphy_evaluation_system;
#INT：如无特殊需要，存放整型数字使用UNSIGNED INT型，整型字段后的数字代表显示长度。比如 id int(11) NOT NULL
#DATETIME：所有需要精确到时间(时分秒)的字段均使用DATETIME,不要使用TIMESTAMP类型。created和updatedtime用timestamp----------已改
#对于精确浮点型数据存储，需要使用DECIMAL，严禁使用FLOAT和DOUBLE。
#如无特殊需要，字段建议使用NOT NULL属性，可用默认值代替NULL。
#自增字段类型必须是整型且必须为UNSIGNED，推荐类型为INT或BIGINT，并且自增字段必须是主键或者主键的一部分。------已解决
#enum看看能不能都拆分成多个表-------已解决
#前面全部改用偏旁部首，数据库中叫做部首radical

CREATE TABLE user (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment'系统用户id',
    login_id VARCHAR(50) UNIQUE NOT NULL comment'登录名id',
    phone VARCHAR(20) not null comment'联系方式',
    name VARCHAR(50) NOT NULL comment'系统用户姓名',
    password VARCHAR(100) NOT NULL comment'密码',
    user_group_id INT UNSIGNED NOT NULL comment'所属用户组id',
    delete_flag tinyint(1) not null DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '系统用户';

insert into user (login_id, phone, name, password, user_group_id,delete_flag)values('112','12345678123','王小虎','112',1,0),
('113','12345678123','王小虎','113',1,0),('114','12345678123','王小虎','114',1,0),('115','12345678123','王小虎','115',1,0),('116','12345678123','王小虎','116',1,0),
('117','12345678123','王小虎','117',1,0),('118','12345678123','王小虎','118',1,0),('119','12345678123','王小虎','119',1,0),('120','12345678123','王小虎','120',1,0),
('121','12345678123','王小虎','121',1,0),('122','12345678123','王小虎','122',1,0),('123','12345678123','王小虎','123',1,0),('124','12345678123','王小虎','124',1,0);

create table user_group (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment'用户组id',
    name varchar(50) not null comment'用户组名',
    state varchar(10) not null comment'状态',
    user_count int not null default 0 comment'该组内系统用户人数',
    description varchar(255) comment '描述',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '用户组';
#给用户组也增加逻辑删除
# ALTER TABLE user_group
# ADD COLUMN delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）';

INSERT INTO user_group (name, state, user_count, description) VALUES ('用户组1', '已激活', 10, '这是用户组1');
INSERT INTO user_group (name, state, user_count, description) VALUES ('用户组2', '已激活', 20, '这是用户组2');
INSERT INTO user_group (name, state, user_count, description) VALUES ('用户组3', '已激活', 5, '这是用户组3');
INSERT INTO user_group (name, state, user_count, description) VALUES ('用户组4', '未激活', 15, '这是用户组4');
INSERT INTO user_group (name, state, user_count, description) VALUES ('用户组5', '未激活', 8, '这是用户组5');

create table permissions (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment'权限id',
    name varchar(50) not null comment '权限名',
    path varchar(255) comment '菜单路径',
    page_path varchar (255) comment '文件路径',
    parent_id int UNSIGNED DEFAULT NULL comment '上一级id',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '权限';

-- 系统用户管理
INSERT INTO permissions (name) VALUES ('系统用户管理');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('系统用户管理', '/backend/user', 'backend/User', 1);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('用户组管理', '/backend/userGroup', 'backend/UserGroup', 1);
-- 基础数据管理
INSERT INTO permissions (name) VALUES ('基础数据管理');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('区域管理', '/backend/region', 'base/Region', 4);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('学校管理', '/backend/school', 'base/School', 4);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('年级管理', '/backend/grade', 'base/Grade', 4);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('学年管理', '/backend/academicYear', 'base/AcademicYear', 4);
-- 前端用户管理
INSERT INTO permissions (name) VALUES ('前端用户管理');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('班级管理', '/backend/klass', 'frontend/Klass', 9);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('学生管理', '/backend/student', 'frontend/Student', 9);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('教师管理', '/backend/teacher', 'frontend/Teacher', 9);
-- 字库管理
INSERT INTO permissions (name) VALUES ('字库管理');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('样本字管理', '/backend/sampleWord', 'word/SampleWord', 13);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('模板字管理', '/backend/templateWord', 'word/TemplateWord', 13);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('字帖管理', '/backend/copybook', 'word/Copybook', 13);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('模板管理', '/backend/template', 'word/Template', 13);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('分类管理', '/backend/define', 'word/Define', 13);
-- 数据分析
INSERT INTO permissions (name) VALUES ('数据分析');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('用户数据分析', '/backend/dataAnalysis', 'analysis/DataAnalysis', 19);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('字库分析', '/backend/fontAnalysis', 'analysis/FontAnalysis', 19);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('书法知识资源分析', '/backend/resourceAnalysis', 'analysis/ResourceAnalysis', 19);
-- 公告与帮助
INSERT INTO permissions (name) VALUES ('公告与帮助');
INSERT INTO permissions (name,parent_id) VALUES ('公告管理', 23);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('发布公告', '/backend/releaseAnnouncement', 'announcementHelp/ReleaseAnnouncement', 24);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('查看公告', '/backend/viewAnnouncement', 'announcementHelp/ViewAnnouncement', 24);
INSERT INTO permissions (name,parent_id) VALUES ('帮助管理',23);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('常见问题与答案设置', '/backend/question', 'announcementHelp/Question', 27);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('建议与反馈管理', '/backend/feedback', 'announcementHelp/Feedback', 27);

-- 竞赛管理
INSERT INTO permissions (name) VALUES ('竞赛管理');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('竞赛列表', '/backend/competitionList', 'competition/CompetitionList', 30);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('评阅管理', '/backend/reviewManagement', 'competition/ReviewManagement', 30);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('评委评分', '/backend/reviewWorks', 'competition/ReviewWorks', 30);
-- 优秀作品管理
INSERT INTO permissions (name) VALUES ('优秀作品管理');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('作业评审', '/backend/reviewOutstanding', 'outstanding/ReviewOutstanding', 34);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('优秀作业作品', '/backend/homeworkSubmissions', 'outstanding/HomeworkSubmissions', 34);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('优秀竞赛作品', '/backend/competitionSubmissions', 'outstanding/CompetitionSubmissions', 34);
-- 书法知识管理
INSERT INTO permissions (name) VALUES ('书法知识管理');
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('类型管理', '/backend/type', 'resource/Type', 38);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('添加书法知识', '/backend/addSource', 'resource/AddSource', 38);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('资源管理', '/backend/resourceManagement', 'resource/ResourceManagement', 38);
INSERT INTO permissions (name, path, page_path, parent_id) VALUES ('视频合集管理', '/backend/collection', 'resource/Collection', 38);

create table user_group_permissions(
    user_group_id int UNSIGNED comment '用户组',
    permissions_id int UNSIGNED comment '权限',
    region_id int UNSIGNED comment '区域',
    primary key (user_group_id,permissions_id),
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '用户组权限';

INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 1);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 2);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 3);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 4);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 5);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 6);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 7);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 8);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 9);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 10);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 11);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 12);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 13);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 14);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 15);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 16);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 17);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 18);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 19);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 20);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 21);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 22);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 23);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 24);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 25);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 26);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 27);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 28);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 29);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 30);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 31);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 32);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 33);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 34);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 35);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 36);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 37);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 38);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 39);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 40);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 41);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (1, 42);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (2, 1);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (2, 2);
INSERT INTO user_group_permissions (user_group_id, permissions_id) VALUES (2, 3);


#
# CREATE TABLE region (
#   id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '区域id',
#   name VARCHAR(25) NOT NULL comment '区域名',
#   parent_id INT UNSIGNED DEFAULT NULL comment '上一级id',
#   level tinyINT NOT NULL comment '等级',
#   updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
# )comment '区域';

CREATE TABLE grade(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '学制年级id',
  name VARCHAR(25) NOT NULL comment '学制年级名',
  parent_id INT UNSIGNED DEFAULT NULL comment '上一级id',
  level tinyint NOT NULL comment'等级',
  updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '学制年级';
INSERT INTO grade (name, parent_id, level) VALUES
('六三制小学', NULL, 1),
('六三制小学一年级', 1, 2),
('六三制小学二年级', 1, 2),
('六三制小学三年级', 1, 2),
('六三制小学四年级', 1, 2),
('六三制小学五年级', 1, 2),
('六三制小学六年级', 1, 2),
('六三制初中', NULL, 1),
('六三制初中初一', 8, 2),
('六三制初中初二', 8, 2),
('六三制初中初三', 8, 2),
('五四制小学', NULL, 1),
('五四制小学一年级', 12, 2),
('五四制小学二年级', 12, 2),
('五四制小学三年级', 12, 2),
('五四制小学四年级', 12, 2),
('五四制小学五年级', 12, 2),
('五四制初中', NULL, 1),
('五四制初中初一', 18, 2),
('五四制初中初二', 18, 2),
('五四制初中初三', 18, 2),
('五四制初中初四', 18, 2);

create table school(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '学校id',
    name varchar(50) not null comment '学校名',
    type varchar(50) not null comment '学校类型',
    leader varchar(50) not null comment '校负责人',
    region_id int UNSIGNED not null comment '区域id',
    address varchar(75) not null comment '地址',
    leader_phone VARCHAR(20) comment'联系方式',
    person_count int default 0 comment '人数',
    delete_flag tinyint(1) not null DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '学校';
# ALTER TABLE school
# ADD COLUMN delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）';
INSERT INTO school (region_id, name, type, leader, address, leader_phone, person_count) VALUES
(1, '北京市第一小学', '公立', '张三', '北京市海淀区', '13800138000', 1000),
(2, '北京市第二小学', '公立', '李四', '北京市海淀区', '13800138001', 900),
(3, '上海市第一中学', '公立', '王五', '上海市杨浦区', '13800138002', 800),
(4, '杭州市第一小学', '公立', '赵六', '浙江省杭州市', '13800138003', 1200),
(5, '南京市第一中学', '公立', '孙七', '江苏省南京市', '13800138004', 1100),
(6, '武汉市第一小学', '公立', '周八', '湖北省武汉市', '13800138005', 1000),
(7, '广州市第一中学', '公立', '吴九', '广东省广州市', '13800138006', 900),
(8, '西安市第一小学', '公立', '郑十', '陕西省西安市', '13800138007', 800),
(9, '成都市第一中学', '公立', '王十一', '四川省成都市', '13800138008', 1200),
(10, '厦门市第一小学', '公立', '李十二', '福建省厦门市', '13800138009', 1100),
(11, '上海市第二中学', '公立', '赵十三', '上海市杨浦区', '13800138010', 1000),
(12, '北京市第三小学', '公立', '孙十四', '北京市海淀区', '13800138011', 900),
(13, '上海市第三中学', '公立', '周十五', '上海市杨浦区', '13800138012', 800),
(14, '南京市第二小学', '公立', '吴十六', '江苏省南京市', '13800138013', 1200),
(15, '广州市第二中学', '公立', '郑十七', '广东省广州市', '13800138014', 1100),
(16, '成都市第二小学', '公立', '王十八', '四川省成都市', '13800138015', 1000),
(17, '哈尔滨市第一中学', '公立', '李十九', '黑龙江省哈尔滨市', '13800138016', 900),
(18, '长春市第一小学', '公立', '赵二十', '吉林省长春市', '13800138017', 800),
(19, '兰州市第一中学', '公立', '孙二十一', '甘肃省兰州市', '13800138018', 1200),
(20, '西安市第二小学', '公立', '周二十二', '陕西省西安市', '13800138019', 1100);



create table klass(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '班级id',
    name varchar(50) not null comment '班级名',
    student_count int default 0 comment '班级人数',
    teacher_id int UNSIGNED comment '所属教师id',
    grade_id int UNSIGNED comment '所属年级id',
    school_id int UNSIGNED not null comment '所属学校id',
    delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    year varchar(4) NOT NULL DEFAULT 2024 COMMENT '入学年份',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '班级';
#班级表加个入校年份，创建时间作为不了入校年份，所以下面的没有执行，上面表已修改
#alter table klass add year varchar(4) not null comment '入学年份';
# ALTER TABLE klass
# ADD COLUMN delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）';
INSERT INTO klass (name, student_count, teacher_id, grade_id, school_id, year)
VALUES
('班级1', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级2', 32, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级3', 28, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级4', 31, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级5', 29, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级6', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级7', 33, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级8', 28, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级9', 32, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级10', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级11', 31, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级12', 29, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级13', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级14', 33, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级15', 28, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级16', 32, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级17', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级18', 31, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级19', 29, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级20', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级21', 33, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级22', 28, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级23', 32, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级24', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级25', 31, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级26', 29, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级27', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级28', 33, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级29', 28, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级30', 32, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级31', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级32', 31, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级33', 29, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级34', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级35', 33, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级36', 28, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级37', 32, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级38', 30, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级39', 31, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE())),
('班级40', 29, 1, FLOOR(1 + RAND() * 9), FLOOR(2 + RAND() * 2), YEAR(CURDATE()));




create table student(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '学生id',
    name varchar(25) not null comment '学生名',
    student_number varchar(50) not null comment '学籍号',
    password VARCHAR(100) NOT NULL comment'密码',
    klass_id int UNSIGNED comment '所属班级id',
    grade_id int UNSIGNED comment '所属年级id',
    school_id int UNSIGNED not null comment '所属学校id',
    phone varchar(25) comment'电话',
    email varchar(50) comment '邮箱',
    region_id int UNSIGNED comment '所在区域id',
    id_number varchar(50) comment'身份证号',
    gender ENUM('男', '女'),
    picture_url varchar(255) comment'头像url',
    delete_flag tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '学生';
# ALTER TABLE student
# MODIFY klass_id int UNSIGNED comment '所属班级id';
# alter table student
# add column grade_id int UNSIGNED comment '所属年级id';
INSERT INTO student
(name, student_number, password, klass_id, school_id, phone, email, region_id, id_number, gender, picture_url, delete_flag)
VALUES
('学生1', '学籍号1', '密码1', 4, 2, '电话1', '邮箱1', 3, '身份证号1', '男', '头像url1', 0),
('学生2', '学籍号2', '密码2', 4, 2, '电话2', '邮箱2', 3, '身份证号2', '女', '头像url2', 0),
('学生3', '学籍号3', '密码3', 4, 2, '电话3', '邮箱3', 3, '身份证号3', '男', '头像url3', 0),
('学生4', '学籍号4', '密码4', 4, 2, '电话4', '邮箱4', 3, '身份证号4', '女', '头像url4', 0),
('学生5', '学籍号5', '密码5', 4, 2, '电话5', '邮箱5', 3, '身份证号5', '男', '头像url5', 0),
('学生6', '学籍号6', '密码6', 4, 2, '电话6', '邮箱6', 3, '身份证号6', '女', '头像url6', 0),
('学生7', '学籍号7', '密码7', 4, 2, '电话7', '邮箱7', 3, '身份证号7', '男', '头像url7', 0),
('学生8', '学籍号8', '密码8', 4, 2, '电话8', '邮箱8', 3, '身份证号8', '女', '头像url8', 0),
('学生9', '学籍号9', '密码9', 4, 2, '电话9', '邮箱9', 3, '身份证号9', '男', '头像url9', 0),
('学生10', '学籍号10', '密码10', 4, 2, '电话10', '邮箱10', 3, '身份证号10', '女', '头像url10', 0),
('学生11', '学籍号11', '密码11', 4, 2, '电话11', '邮箱11', 3, '身份证号11', '男', '头像url11', 0),
('学生12', '学籍号12', '密码12', 4, 2, '电话12', '邮箱12', 3, '身份证号12', '女', '头像url12', 0),
('学生13', '学籍号13', '密码13', 4, 2, '电话13', '邮箱13', 3, '身份证号13', '男', '头像url13', 0),
('学生14', '学籍号14', '密码14', 4, 2, '电话14', '邮箱14', 3, '身份证号14', '女', '头像url14', 0),
('学生15', '学籍号15', '密码15', 4, 2, '电话15', '邮箱15', 3, '身份证号15', '男', '头像url15', 0),
('学生16', '学籍号16', '密码16', 4, 2, '电话16', '邮箱16', 3, '身份证号16', '女', '头像url16', 0),
('学生17', '学籍号17', '密码17', 4, 2, '电话17', '邮箱17', 3, '身份证号17', '男', '头像url17', 0),
('学生18', '学籍号18', '密码18', 4, 2, '电话18', '邮箱18', 3, '身份证号18', '女', '头像url18', 0),
('学生19', '学籍号19', '密码19', 4, 2, '电话19', '邮箱19', 3, '身份证号19', '男', '头像url19', 0),
('学生20', '学籍号20', '密码20', 4, 2, '电话20', '邮箱20', 3, '身份证号20', '女', '头像url20', 0),
('学生21', '学籍号21', '密码21', 4, 2, '电话21', '邮箱21', 3, '身份证号21', '男', '头像url21', 0),
('学生22', '学籍号22', '密码22', 4, 2, '电话22', '邮箱22', 3, '身份证号22', '女', '头像url22', 0),
('学生23', '学籍号23', '密码23', 4, 2, '电话23', '邮箱23', 3, '身份证号23', '男', '头像url23', 0),
('学生24', '学籍号24', '密码24', 4, 2, '电话24', '邮箱24', 3, '身份证号24', '女', '头像url24', 0),
('学生25', '学籍号25', '密码25', 4, 2, '电话25', '邮箱25', 3, '身份证号25', '男', '头像url25', 0),
('学生26', '学籍号26', '密码26', 4, 2, '电话26', '邮箱26', 3, '身份证号26', '女', '头像url26', 0),
('学生27', '学籍号27', '密码27', 4, 2, '电话27', '邮箱27', 3, '身份证号27', '男', '头像url27', 0),
('学生28', '学籍号28', '密码28', 4, 2, '电话28', '邮箱28', 3, '身份证号28', '女', '头像url28', 0),
('学生29', '学籍号29', '密码29', 4, 2, '电话29', '邮箱29', 3, '身份证号29', '男', '头像url29', 0),
('学生30', '学籍号30', '密码30', 4, 2, '电话30', '邮箱30', 3, '身份证号30', '女', '头像url30', 0);

create table teacher(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '教师id',
    name varchar(25) not null comment '教师名',
    workno VARCHAR(50) NOT NULL comment'工号',
    password VARCHAR(100) NOT NULL comment'密码',
    id_number varchar(50) comment'身份证号',
    gender ENUM('男', '女') comment '性别',
    phone varchar(25) UNIQUE NOT NULL comment'电话',
    email varchar(50) comment '邮箱',
    school_id int UNSIGNED not null comment '所属学校id',
    region_id int UNSIGNED comment '所在区域id',
    avatar varchar(255) comment'头像url',
    delete_flag tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '教师';
# alter table teacher
#     add column school_id int UNSIGNED not null comment '所属学校id';
INSERT INTO teacher
(name, workno, password, id_number, gender, phone, email, school_id, region_id, picture_url, delete_flag)
VALUES
('教师1', '111', '111', '身份证号1', '男', '电话1', '邮箱1', 2, 3, '头像url1', 0),
('教师2', '112', '112', '身份证号2', '女', '电话2', '邮箱2', 2, 3, '头像url2', 0),
('教师3', '113', '113', '身份证号3', '男', '电话3', '邮箱3', 2, 3, '头像url3', 0),
('教师4', '114', '114', '身份证号4', '女', '电话4', '邮箱4', 2, 3, '头像url4', 0),
('教师5', '115', '115', '身份证号5', '男', '电话5', '邮箱5', 2, 3, '头像url5', 0),
('教师6', '116', '116', '身份证号6', '女', '电话6', '邮箱6', 2, 3, '头像url6', 0),
('教师7', '117', '117', '身份证号7', '男', '电话7', '邮箱7', 2, 3, '头像url7', 0),
('教师8', '118', '118', '身份证号8', '女', '电话8', '邮箱8', 2, 3, '头像url8', 0),
('教师9', '119', '119', '身份证号9', '男', '电话9', '邮箱9', 2, 3, '头像url9', 0),
('教师10', '工号10', '密码10', '身份证号10', '女', '电话10', '邮箱10', 2, 3, '头像url10', 0),
('教师11', '工号11', '密码11', '身份证号11', '男', '电话11', '邮箱11', 2, 3, '头像url11', 0),
('教师12', '工号12', '密码12', '身份证号12', '女', '电话12', '邮箱12', 2, 3, '头像url12', 0),
('教师13', '工号13', '密码13', '身份证号13', '男', '电话13', '邮箱13', 2, 3, '头像url13', 0),
('教师14', '工号14', '密码14', '身份证号14', '女', '电话14', '邮箱14', 2, 3, '头像url14', 0),
('教师15', '工号15', '密码15', '身份证号15', '男', '电话15', '邮箱15', 2, 3, '头像url15', 0),
('教师16', '工号16', '密码16', '身份证号16', '女', '电话16', '邮箱16', 2, 3, '头像url16', 0),
('教师17', '工号17', '密码17', '身份证号17', '男', '电话17', '邮箱17', 2, 3, '头像url17', 0),
('教师18', '工号18', '密码18', '身份证号18', '女', '电话18', '邮箱18', 2, 3, '头像url18', 0),
('教师19', '工号19', '密码19', '身份证号19', '男', '电话19', '邮箱19', 2, 3, '头像url19', 0),
('教师20', '工号20', '密码20', '身份证号20', '女', '电话20', '邮箱20', 2, 3, '头像url20', 0),
('教师21', '工号21', '密码21', '身份证号21', '男', '电话21', '邮箱21', 2, 3, '头像url21', 0),
('教师22', '工号22', '密码22', '身份证号22', '女', '电话22', '邮箱22', 2, 3, '头像url22', 0),
('教师23', '工号23', '密码23', '身份证号23', '男', '电话23', '邮箱23', 2, 3, '头像url23', 0),
('教师24', '工号24', '密码24', '身份证号24', '女', '电话24', '邮箱24', 2, 3, '头像url24', 0),
('教师25', '工号25', '密码25', '身份证号25', '男', '电话25', '邮箱25', 2, 3, '头像url25', 0),
('教师26', '工号26', '密码26', '身份证号26', '女', '电话26', '邮箱26', 2, 3, '头像url26', 0),
('教师27', '工号27', '密码27', '身份证号27', '男', '电话27', '邮箱27', 2, 3, '头像url27', 0),
('教师28', '工号28', '密码28', '身份证号28', '女', '电话28', '邮箱28', 2, 3, '头像url28', 0),
('教师29', '工号29', '密码29', '身份证号29', '男', '电话29', '邮箱29', 2, 3, '头像url29', 0),
('教师30', '工号30', '密码30', '身份证号30', '女', '电话30', '邮箱30', 2, 3, '头像url30', 0);





CREATE TABLE academic_year (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_start DATETIME comment '第一学期开始日期',
    first_end DATETIME comment '第一学期结束日期',
    second_start DATETIME comment '第二学期开始日期',
    second_end DATETIME comment '第二学期结束日期'
)comment '学年';


/*
#下面这个表干什么用的不知道
create table frontend_user_mangement(
    teacher_id int UNSIGNED not null comment '教师id',
    klass_id int UNSIGNED not null comment '班级id',
    academic_year_id int UNSIGNED not null comment '学制年级id',
    school_id int UNSIGNED not null comment '学校id',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '教师班级学校关系表';
*/

create table homework(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '作业id',
    name varchar(50) not null comment '作业名',
    type enum('综合','专项','字帖'),
    detail_type enum ('部首','结构') NULL,
    font_id int UNSIGNED not null comment '字体id',
    word_count int not null comment '字数',
    requirements varchar(500) comment '作业要求',
    deadline datetime not null comment '截止时间',
    difficulty tinyint(1) not null comment '难度',
    target ENUM('个人', '集体') comment '发布对象',
    is_self tinyint(1) not null default 0 comment '是否为自我生成的作业作品',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '作业';

create table homework_image(
    homework_id int UNSIGNED comment '作业id',
    picture_url varchar(255) not null comment '图片url',
    primary key (homework_id,picture_url)
);
#front改成font，上表已改

#创建作业的时候要给每个学生都创建一个空的作业作品
create table homework_submission(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '作业作品id',
    system_score tinyint comment '系统评分',
    system_feedback varchar(255) comment '系统评语',
    teacher_score tinyint comment '教师得分',
    teacher_feedback varchar(255) comment '教师评语',
    homework_id int UNSIGNED not null comment '作业id',
    student_id int UNSIGNED not null comment '学生id',
    state tinyint(1) not null default 0 comment '学生是否完成作业',
    reviewed tinyint(1) not null default 0 comment '作业是否被教师批改，0为未批改，1为中间态，2为已批改',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '作业作品';
#作业id改成作业作品id，上表已更改
create table hsubmission_image(
    submission_id int UNSIGNED comment '作业id',
    picture_url varchar(255) not null comment '图片url',
    primary key (submission_id,picture_url)
);


# create table student_homework(
#     homework_id int UNSIGNED not null comment '作业id',
#     student_id int UNSIGNED not null comment '学生id',
#     primary key (student_id,homework_id)
# )comment '通过此查找个人作业，个人作业表';
#
create table klass_homework(
    homework_id int UNSIGNED not null comment '作业id',
    klass_id int UNSIGNED not null comment '班级id',
    primary key (klass_id,homework_id)
)comment '集体作业表';
#student_id 要和消息一样改成学生和班级的id，然后下面来个类型,已分成两表
#作业作品id去掉，在作业作品里加一个作业id，上两表已修改，，，，，上面两表的结果是已经思考过了的
#下面主要是为了查询，查询的同时还需要往学生作业表中添加，表示该学生有作业了
#2024/8/5，布置作业时，自动创建作业作品，取消掉个人作业表，保留集体作业表

create table teacher_homework(
    teacher_id int UNSIGNED not null comment '教师id',
    homework_id int UNSIGNED not null comment '作业id',
    template_id int UNSIGNED not null comment '模板id',
    template_type enum('自定义','系统') comment '模板类型',
    primary key (teacher_id,homework_id)
)comment '教师对作业的布置，同时记录是否使用了模板';
#教师、模板、作业之间有联系了，，，如果是通过已有作业来创建的，就把已有作业的东西复制到模板里成为自定义模板
#上面是错的，加了个教师、自定义模板的关系表,每使用一次记得把使用次数增加，错了，2024/8/8教师直接放在自定义模板里
# create table teacher_template(
#     teacher_id int UNSIGNED not null comment '教师id',
#     template_id int UNSIGNED not null comment '模板id',
#     count int default 0 comment '使用次数',
#     primary key (teacher_id,template_id)
# );
#每发布一次作业时记得把使用次数增加


create table student_homework(
    student_id int UNSIGNED not null comment '学生id',
    homework_id int UNSIGNED not null comment '作业id',
    template_id int UNSIGNED not null comment '模板id',
    template_type enum('自定义','系统') comment '模板类型',
    primary key (student_id,homework_id)
)comment '学生创建作业的表';
#上表是学生创建作业的表

create table custom_template(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '模板id',
    name varchar(50) not null comment '模板名',
    type enum('综合','专项','字帖'),
    detail_type enum ('部首','结构') NULL,
    font_id int UNSIGNED not null comment '字体id',
    creator_id int UNSIGNED not null comment'创作人id',
    difficulty enum('1','2','3','4','5') not null comment '难度',
    word_count int comment '字数',
    count int default 0 comment '使用次数',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '自定义模板';
#已更改上表font
create table custom_template_image(
    custom_template_id int UNSIGNED  comment '模板id',
    picture_url varchar(255) not null comment '图片url',
    primary key (custom_template_id,picture_url)
);
create table system_template(
    id int AUTO_INCREMENT PRIMARY KEY comment '模板id',
    name varchar(50) not null comment '模板名',
    type enum('综合','专项','字帖'),
    detail_type enum ('部首','结构') NULL,
    font_id int not null comment '字体id',
    creator_id int not null comment'创作人id',
    difficulty enum('1','2','3','4','5') not null comment '难度',
    word_count int comment '字数',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '系统模板';
#已更改上表font，字体id可能要考虑要不要去掉，因为字帖模板，详细类型也要改，笔画去掉，未操作删改
create table system_template_image(
    system_template_id int UNSIGNED comment '模板id',
    picture_url varchar(255) not null comment '图片url',
    primary key (system_template_id,picture_url)
);

create table font(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '字体id',
    name varchar(25) not null comment '字体名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '字体';
#字体已改成font
INSERT INTO font (name) VALUES
('楷体-赵淑安'),
('楷体-李明'),
('楷体-王华'),
('楷体-张磊'),
('楷体-刘洋'),
('楷体-陈磊'),
('楷体-杨洋'),
('楷体-黄磊'),
('楷体-周华'),
('楷体-吴磊'),
('楷体-郑洋'),
('楷体-冯华'),
('楷体-陈洋'),
('楷体-楚磊'),
('楷体-魏洋');



create table stroke (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '笔画id',
    name varchar(25) not null comment '笔画名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '笔画';
INSERT INTO stroke (name) VALUES
('点'),
('横'),
('竖'),
('撇'),
('提'),
('横折'),
('横撇/横钩'),
('横折钩'),
('竖弯钩'),
('横折弯钩/横斜钩'),
('竖折折钩'),
('横折折/横折弯'),
('横折提'),
('横折折折钩/横撇弯钩'),
('竖折撇/竖折折'),
('横折折撇'),
('捺'),
('斜钩'),
('弯钩'),
('竖钩'),
('竖折/竖弯'),
('撇折'),
('撇点'),
('竖提');


create table radical (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '偏旁id',
    name varchar(25) not null comment '偏旁名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '部首';

INSERT INTO radical (name) VALUES
('丨'), ('亅'), ('丿'), ('乛'), ('一'), ('乙'), ('乚'), ('丶'),
('八'), ('勹'), ('匕'), ('冫'), ('卜'), ('厂'), ('刀'), ('刂'), ('儿'), ('二'), ('匚'), ('阝'), ('丷'), ('几'), ('卩'), ('冂'), ('力'), ('冖'), ('凵'), ('人'), ('亻'), ('入'), ('十'), ('厶'), ('亠'), ('匸'), ('讠'), ('廴'), ('又'),
('艹'), ('屮'), ('彳'), ('巛'), ('川'), ('辶'), ('寸'), ('大'), ('飞'), ('干'), ('工'), ('弓'), ('廾'), ('广'), ('己'), ('彐'), ('彑'), ('巾'), ('口'), ('马'), ('门'), ('宀'), ('女'), ('犭'), ('山'), ('彡'), ('尸'), ('饣'), ('士'), ('扌'), ('氵'), ('纟'), ('巳'), ('土'), ('囗'), ('兀'), ('夕'), ('小'), ('忄'), ('幺'), ('弋'), ('尢'), ('夂'), ('子'),
('贝'), ('比'), ('灬'), ('长'), ('车'), ('歹'), ('斗'), ('厄'), ('方'), ('风'), ('父'), ('戈'), ('卝'), ('户'), ('火'), ('旡'), ('见'), ('斤'), ('耂'), ('毛'), ('木'), ('肀'), ('牛'), ('牜'), ('爿'), ('片'), ('攴'), ('攵'), ('气'), ('欠'), ('犬'), ('日'), ('氏'), ('礻'), ('手'), ('殳'), ('水'), ('瓦'), ('尣'), ('王'), ('韦'), ('文'), ('毋'), ('心'), ('牙'), ('爻'), ('曰'), ('月'), ('爫'), ('支'), ('止'), ('爪'),
('白'), ('癶'), ('歺'), ('甘'), ('瓜'), ('禾'), ('钅'), ('立'), ('龙'), ('矛'), ('皿'), ('母'), ('目'), ('疒'), ('鸟'), ('皮'), ('生'), ('石'), ('矢'), ('示'), ('罒'), ('田'), ('玄'), ('穴'), ('疋'), ('业'), ('衤'), ('用'), ('玉'),
('耒'), ('艸'), ('臣'), ('虫'), ('而'), ('耳'), ('缶'), ('艮'), ('虍'), ('臼'), ('米'), ('齐'), ('肉'), ('色'), ('舌'), ('覀'), ('页'), ('先'), ('行'), ('血'), ('羊'), ('聿'), ('至'), ('舟'), ('衣'), ('竹'), ('自'), ('羽'), ('糸'), ('糹'),
('貝'), ('采'), ('镸'), ('车'), ('辰'), ('赤'), ('辵'), ('豆'), ('谷'), ('见'), ('角'), ('克'), ('里'), ('卤'), ('麦'), ('身'), ('豕'), ('辛'), ('言'), ('邑'), ('酉'), ('豸'), ('走'), ('足'),
('青'), ('靑'), ('雨'), ('齿'), ('长'), ('非'), ('阜'), ('金'), ('釒'), ('隶'), ('门'), ('靣'), ('飠'), ('鱼'), ('隹'),
('风'), ('革'), ('骨'), ('鬼'), ('韭'), ('面'), ('首'), ('韋'), ('香'), ('页'), ('音'),
('髟'), ('鬯'), ('鬥'), ('高'), ('鬲'), ('马'),
('黄'), ('鹵'), ('鹿'), ('麻'), ('麦'), ('鸟'), ('鱼'),
('鼎'), ('黑'), ('黽'), ('黍'), ('黹'),
('鼓'), ('鼠'),
('鼻'), ('齊'),
('齿'), ('龍'), ('龠');

create table structure (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '结构id',
    name varchar(25) not null comment '结构名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '结构';
INSERT INTO structure (name) VALUES
('上三包围结构'),
('上下结构'),
('上中下结构'),
('下三包围结构'),
('全包围结构'),
('右上包围结构'),
('品字形结构'),
('左三包围结构'),
('左上包围结构'),
('左下包围结构'),
('左中右结构'),
('左右结构');



create table template_word(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '模板字id',
    name varchar(15) not null comment '模板字名',
    author varchar(15) not null comment '书写的书法家名,即作者',
    structure_id int UNSIGNED not null comment '结构id',
    font_id int UNSIGNED not null comment '字体id',
    radical_id int UNSIGNED not null comment '偏旁id',
    content varchar(255) comment'模板字图片url',
    importer varchar(25) comment'导入人',
    grade_id int UNSIGNED not null comment '学制年级id',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '模板字';
# alter table template_word add column author varchar(15) not null comment '书写的书法家名,即作者';
#已更改上表font
INSERT INTO template_word (name, structure_id, radical_id, grade_id, font_id,content, author, importer) VALUES
('样本字1', 1, 1, 1,1, '/images/word/1.png', '学生作品', '导入人1'),
('样本字2', 2, 2, 2,1, '/images/word/2.png', '竞赛作品', '导入人2'),
('样本字3', 3, 3, 3,1, '/images/word/3.png', '社会取得', '导入人3'),
('样本字4', 4, 4, 4,1, '/images/word/4.png', '学生作品', '导入人4'),
('样本字5', 5, 5, 5,1, '/images/word/5.png', '竞赛作品', '导入人5'),
('样本字6', 6, 6, 6,1, '/images/word/6.png', '社会取得', '导入人1'),
('样本字7', 7, 7, 7,1, '/images/word/7.png', '学生作品', '导入人2'),
('样本字8', 8, 8, 8,1, '/images/word/8.png', '竞赛作品', '导入人3'),
('样本字9', 9, 9, 9,1, '/images/word/9.png', '社会取得', '导入人4'),
('样本字10', 10, 1, 1,1, '/images/word/10.png', '学生作品', '导入人5'),
('样本字11', 1, 3, 4, 1,'/images/word/11.png', '竞赛作品', '导入人1'),
('样本字12', 2, 4, 5, 1,'/images/word/12.png', '社会取得', '导入人2'),
('样本字13', 3, 5, 6, 1,'/images/word/13.png', '学生作品', '导入人3'),
('样本字14', 4, 6, 7, 1,'/images/word/14.png', '竞赛作品', '导入人4'),
('样本字15', 5, 7, 8, 1,'/images/word/15.png', '社会取得', '导入人5'),
('样本字16', 6, 8, 9, 1,'/images/word/16.png', '学生作品', '导入人1'),
('样本字17', 7, 9, 10,1, '/images/word/17.png', '竞赛作品', '导入人2'),
('样本字18', 8, 10, 1,1, '/images/word/18.png', '社会取得', '导入人3'),
('样本字19', 9, 1, 2, 1,'/images/word/19.png', '学生作品', '导入人4'),
('样本字20', 10, 2, 3,1, '/images/word/20.png', '竞赛作品', '导入人5'),
('样本字21', 1, 3, 4, 1,'/images/word/21.png', '社会取得', '导入人1'),
('样本字22', 2, 4, 5, 1,'/images/word/22.png', '学生作品', '导入人2'),
('样本字23', 3, 5, 6, 1,'/images/word/23.png', '竞赛作品', '导入人3'),
('样本字24', 4, 6, 7, 1,'/images/word/24.png', '社会取得', '导入人4'),
('样本字25', 5, 7, 8, 1,'/images/word/25.png', '学生作品', '导入人5'),
('样本字26', 6, 8, 9, 1,'/images/word/26.png', '竞赛作品', '导入人1'),
('样本字27', 7, 9, 10,1, '/images/word/27.png', '社会取得', '导入人2'),
('样本字28', 8, 10, 1,1, '/images/word/28.png', '学生作品', '导入人3'),
('样本字29', 10, 1, 2,1, '/images/word/29.png', '竞赛作品', '导入人4'),
('样本字30', 10, 2, 3,1, '/images/word/30.png', '社会取得', '导入人5');

create table copybook(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '字帖id',
    name varchar(35) not null comment '字帖名',
    author varchar(25) comment'作者',
    font_id int UNSIGNED not null comment '字体id',
    importer varchar(25) comment'导入人',
    content varchar(255) comment'字帖图片url',
    grade_id int unsigned comment '学制年级',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '字帖';
# alter table copybook add column grade_id int UNSIGNED comment '所属年级id';
#已更改上表font
INSERT INTO copybook (name, author, font_id, importer, content, grade_id) VALUES
('字帖1', '作者1', 1, '导入人1', '/images/copybook/1.jpg', 2),
('字帖2', '作者2', 2, '导入人2', '/images/copybook/2.jpg', 2),
('字帖3', '作者3', 3, '导入人3', '/images/copybook/3.jpg', 2),
('字帖4', '作者4', 4, '导入人4', '/images/copybook/4.jpg', 2),
('字帖5', '作者5', 5, '导入人5', '/images/copybook/5.jpg', 2),
('字帖6', '作者6', 6, '导入人6', '/images/copybook/6.jpg', 2),
('字帖7', '作者7', 7, '导入人7', '/images/copybook/7.jpg', 2),
('字帖8', '作者8', 8, '导入人8', '/images/copybook/8.jpg', 2),
('字帖9', '作者9', 9, '导入人9', '/images/copybook/9.jpg', 2),
('字帖10', '作者10', 1, '导入人10', '/images/copybook/1.jpg', 3),
('字帖11', '作者11', 1, '导入人11', '/images/copybook/2.jpg', 3),
('字帖12', '作者12', 1, '导入人12', '/images/copybook/3.jpg', 3),
('字帖13', '作者13', 1, '导入人13', '/images/copybook/4.jpg', 3),
('字帖14', '作者14', 1, '导入人14', '/images/copybook/5.jpg', 3),
('字帖15', '作者15', 1, '导入人15', '/images/copybook/6.jpg', 3),
('字帖16', '作者16', 1, '导入人16', '/images/copybook/7.jpg', 3),
('字帖17', '作者17', 1, '导入人17', '/images/copybook/8.jpg', 3),
('字帖18', '作者18', 1, '导入人18', '/images/copybook/9.jpg', 3),
('字帖19', '作者19', 1, '导入人19', '/images/copybook/1.jpg', 3),
('字帖20', '作者20', 2, '导入人20', '/images/copybook/2.jpg', 3),
('字帖21', '作者21', 2, '导入人21', '/images/copybook/3.jpg', 3),
('字帖22', '作者22', 2, '导入人22', '/images/copybook/4.jpg', 3),
('字帖23', '作者23', 2, '导入人23', '/images/copybook/5.jpg', 3),
('字帖24', '作者24', 2, '导入人24', '/images/copybook/6.jpg', 3),
('字帖25', '作者25', 2, '导入人25', '/images/copybook/7.jpg', 3),
('字帖26', '作者26', 2, '导入人26', '/images/copybook/8.jpg', 3),
('字帖27', '作者27', 2, '导入人27', '/images/copybook/9.jpg', 3),
('字帖28', '作者28', 2, '导入人28', '/images/copybook/1.jpg', 3),
('字帖29', '作者29', 2, '导入人29', '/images/copybook/2.jpg', 3),
('字帖30', '作者30', 3, '导入人30', '/images/copybook/3.jpg', 3);


create table sample_word(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '样本字id',
    name varchar(15) not null comment '样本字名',
    structure_id int UNSIGNED not null comment '结构id',
    radical_id int UNSIGNED not null comment '偏旁id',
    grade_id int UNSIGNED comment '所属年级id',
    content varchar(255) comment'字帖图片url',
    source varchar(50) comment '来源',
    importer varchar(25) comment'导入人',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '样本字';
#  alter table sample_word add column importer varchar(25) comment'导入人';
#已更改上表font
INSERT INTO sample_word (name, structure_id, radical_id, grade_id, content, source, importer) VALUES
('样本字1', 1, 1, 1, '/images/word/1.png', '学生作品', '导入人1'),
('样本字2', 2, 2, 2, '/images/word/2.png', '竞赛作品', '导入人2'),
('样本字3', 3, 3, 3, '/images/word/3.png', '社会取得', '导入人3'),
('样本字4', 4, 4, 4, '/images/word/4.png', '学生作品', '导入人4'),
('样本字5', 5, 5, 5, '/images/word/5.png', '竞赛作品', '导入人5'),
('样本字6', 6, 6, 6, '/images/word/6.png', '社会取得', '导入人1'),
('样本字7', 7, 7, 7, '/images/word/7.png', '学生作品', '导入人2'),
('样本字8', 8, 8, 8, '/images/word/8.png', '竞赛作品', '导入人3'),
('样本字9', 9, 9, 9, '/images/word/9.png', '社会取得', '导入人4'),
('样本字10', 10, 1, 1, '/images/word/10.png', '学生作品', '导入人5'),
('样本字11', 1, 3, 4, '/images/word/11.png', '竞赛作品', '导入人1'),
('样本字12', 2, 4, 5, '/images/word/12.png', '社会取得', '导入人2'),
('样本字13', 3, 5, 6, '/images/word/13.png', '学生作品', '导入人3'),
('样本字14', 4, 6, 7, '/images/word/14.png', '竞赛作品', '导入人4'),
('样本字15', 5, 7, 8, '/images/word/15.png', '社会取得', '导入人5'),
('样本字16', 6, 8, 9, '/images/word/16.png', '学生作品', '导入人1'),
('样本字17', 7, 9, 10, '/images/word/17.png', '竞赛作品', '导入人2'),
('样本字18', 8, 10, 1, '/images/word/18.png', '社会取得', '导入人3'),
('样本字19', 9, 1, 2, '/images/word/19.png', '学生作品', '导入人4'),
('样本字20', 10, 2, 3, '/images/word/20.png', '竞赛作品', '导入人5'),
('样本字21', 1, 3, 4, '/images/word/21.png', '社会取得', '导入人1'),
('样本字22', 2, 4, 5, '/images/word/22.png', '学生作品', '导入人2'),
('样本字23', 3, 5, 6, '/images/word/23.png', '竞赛作品', '导入人3'),
('样本字24', 4, 6, 7, '/images/word/24.png', '社会取得', '导入人4'),
('样本字25', 5, 7, 8, '/images/word/25.png', '学生作品', '导入人5'),
('样本字26', 6, 8, 9, '/images/word/26.png', '竞赛作品', '导入人1'),
('样本字27', 7, 9, 10, '/images/word/27.png', '社会取得', '导入人2'),
('样本字28', 8, 10, 1, '/images/word/28.png', '学生作品', '导入人3'),
('样本字29', 10, 1, 2, '/images/word/29.png', '竞赛作品', '导入人4'),
('样本字30', 10, 2, 3, '/images/word/30.png', '社会取得', '导入人5');


create table feedback(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '反馈id',
    provider_id int UNSIGNED not null comment '反馈人id',
    provider_type enum('教师','学生','管理员') not null comment '反馈人类型',
    type varchar(50) not null comment '反馈性质',
    state varchar(25) default '未评判' comment '反馈有效性',
    provider_phone varchar(20) comment '反馈人电话',
    editor varchar(20) comment '编辑人，即回复人',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '反馈';

create table feedback_content(
    feedback_id int UNSIGNED primary key comment '反馈id',
    message varchar(1000) not null comment '反馈文本内容',
    reply varchar(1000) comment '回复内容',
    file varchar(555) comment '反馈附件url'
)comment'反馈内容';

create table announcement(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '公告id',
    name varchar(45) not null comment '公告名',
    target enum('学生','教师','学生和教师','管理员','全体') comment '发布对象',
    state varchar(25) comment '公告状态',
    release_time datetime default now() comment '发布时间',
    publisher varchar(30) comment '发布人',
    type varchar(30) not null comment '公告类型',
    picture_url varchar(255) comment'封面图片url',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '公告';

create table announcement_content(
    announcement_id int UNSIGNED not null primary key comment '公告id',
    message varchar(3000) not null comment '公告内容'
)comment '公告内容';

#对话记录是暂时的，即用完就删，故存html，，，，，客服对话记录删了，不要了，不要客服了
# create table chat(
#     id bigint unsigned AUTO_INCREMENT PRIMARY KEY comment '对话记录id',
#     sender_id int not null comment '发送人id',
#     sender_type enum('教师','学生','管理员'),
#     receiver_id int not null comment '接收人id',
#     state varchar(25) not null comment '状态',
#     content varchar(2000) comment '对话记录内容html',
#     created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
# )comment '客服对话记录';

#此处把属性question和answer改为q和a
create table question(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '常见问题id',
    q  varchar(200) not null comment '问题',
    title varchar(30) not null comment '标题',
    type varchar(30) comment '种类',
    a varchar(400) not null comment '答案',
    editor varchar(20) comment '编辑人，即回复人'
)comment '常见问题';

create table competition(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '竞赛id',
    name varchar(50) not null comment '竞赛名称',
    registration_start_time datetime not null comment '开始报名时间',
    registration_end_time datetime not null comment '结束报名时间',
    competition_start_time datetime not null comment '开始竞赛时间',
    competition_end_time datetime not null comment '结束竞赛时间',
    review_start_time datetime not null comment '开始评阅时间',
    count int not null default 0 comment '参赛人数',
    state varchar(50) default '准备报名中' comment '状态',
    picture varchar(255) comment '宣传图url',
    delete_flag tinyint(1) not null DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '竞赛';

create table klass_competition(
    klass_id int UNSIGNED not null comment '班级id',
    competition_id int UNSIGNED not null comment '竞赛id',
    primary key (competition_id,klass_id)
)comment '班级报名竞赛表';

create table competition_requirements(
    competition_id int UNSIGNED not null primary key comment '竞赛id',
    requirements varchar(2000) not null comment '竞赛要求'
)comment '竞赛要求和宣传图';

create table division(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '组别id',
    name varchar(50) not null comment '组别名称',
    competition_id int UNSIGNED not null comment '竞赛id',
    special_prize_ratio float default 2.5 comment '特等奖比例',
    first_prize_ratio float default 5.5 comment '一等奖比例',
    second_prize_ratio float default 10 comment '二等奖比例',
    third_prize_ratio float default 18 comment '三等奖比例',
    target enum('小学','初中','小学和初中') comment '参赛对象',
    state varchar(10) default '同竞赛状态' comment '该组别的状态',
    delete_flag tinyint(1) not null DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '组别';

create table division_requirements(
    division_id int UNSIGNED not null primary key comment '组别id',
    requirements varchar(2000) not null comment '组别要求'
)comment '组别要求';

create table competition_submissions(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '竞赛作品id',
    name varchar(30) comment '作品名称',
    competition_id int UNSIGNED not null comment '竞赛id',
    division_id int UNSIGNED not null comment '组别id',
    author_id int UNSIGNED not null comment '作者id',
    initial_score tinyint default -1 comment '初级评分',
    initial_rank int comment '初级评分排名',
    teacher_id int UNSIGNED comment '评阅教师id',
    initial_evaluation varchar(255) comment '初级评价',
    system_score tinyint comment '系统评分',
    system_evaluation varchar(255)comment '系统评价',
    average_final_score decimal(5,3) comment '最终评分均分',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '竞赛作品';

create table csubmission_image(
    submission_id int UNSIGNED comment '作业id',
    picture_url varchar(255) not null comment '图片url',
    primary key (submission_id,picture_url)
)comment '竞赛作品的提交图片';

create table participant(
    division_id int UNSIGNED comment '组别id',
    student_id int UNSIGNED comment '学生id',
    competition_id int UNSIGNED comment '竞赛id',
    submission_id int UNSIGNED default 0 comment '学生作品id',
    primary key (division_id,student_id)
)comment '竞赛参赛人员表';

create table reviewers(
    division_id int UNSIGNED comment '组别id',
    teacher_id int UNSIGNED comment '评阅教师id就是教师的id',
    is_urged int unsigned default 0 comment '是否被催促',
    primary key (division_id,teacher_id)
)comment '评阅教师表';

create table final_review(
    teacher_id int UNSIGNED comment '评阅教师id',
    submission_id int UNSIGNED comment '学生作品id',
    score tinyint comment '该老师评此作品评分',
    primary key (teacher_id,submission_id)
)comment '最终评阅记录';


create table final_rank(
    submission_id int UNSIGNED primary key comment '作品id',
    rk int comment '排名',
    division_id int UNSIGNED comment '组别id',
    level varchar(10) comment '等级如一等奖',
    score tinyint not null comment '最终评分得分',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '最终评分排名';


create table competition_rules(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '规则id',
    question varchar(50) not null comment '评阅规则内容，如系统初筛分数',
    answer int not null comment '规则结果，可修改',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '评阅规则';


create table calligraphy_facts(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '书法知识类型id',
    name VARCHAR(25) NOT NULL comment '书法知识类型名',
    parent_id INT UNSIGNED DEFAULT NULL comment '上一级id',
    level tinyINT NOT NULL comment '等级',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '书法知识类型';
INSERT INTO calligraphy_facts (name, parent_id, level) VALUES
('教学指导', NULL, 1),
('教学指导', 1, 2),
('字的书写', 1, 2),
('书法知识', null, 1),
('名家作品', 4, 2),
('汉字', 4, 2),
('人物传记', 4, 2);


# create table calligraphy_resources(
#     id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '书法知识类型id',
#     name VARCHAR(100) NOT NULL comment '书法知识类型名',
#     first_type_id int UNSIGNED not null comment '第一类型id',
#     second_type_id int UNSIGNED not null comment '第二类型id',
#     custom_type varchar(255) comment '自定义类型',
#     content varchar(255) not null comment '资源文件url',
#     created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
# )comment '书法知识资源';
# drop table calligraphy_resources;#删了是因为分成下面两个表了

create table article(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '文章id',
    name VARCHAR(100) NOT NULL comment '文章名',
    first_type_id int UNSIGNED not null comment '第一类型id',
    second_type_id int UNSIGNED not null comment '第二类型id',
    tag varchar(355) comment '自定义类型',
    is_recommended tinyint(1) not null DEFAULT 0 COMMENT'1代表是推荐作品，0代表不是推荐作品',
    picture_url varchar(255) comment'封面url',
    importer varchar(25) comment'导入人',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '书法知识文章表';

CREATE TABLE article_contents (
    id INT PRIMARY KEY,
    content TEXT
)comment '书法知识文章内容表';

create table video(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '视频id',
    name VARCHAR(100) NOT NULL comment '视频名',
    first_type_id int UNSIGNED not null comment '第一类型id',
    second_type_id int UNSIGNED not null comment '第二类型id',
    tag varchar(255) comment '自定义类型',
    is_recommended tinyint(1) not null DEFAULT 0 COMMENT'1代表是推荐作品，0代表不是推荐作品',
    content varchar(255) not null comment '资源文件url',
    picture_url varchar(255) comment'封面url',
    summary varchar(1000) comment '简介',
    importer varchar(25) comment'导入人',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '书法知识视频表';

create table collection(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '合集id',
    name VARCHAR(100) NOT NULL comment '合集名',
    first_type_id int UNSIGNED not null comment '第一类型id',
    second_type_id int UNSIGNED not null comment '第二类型id',
    tag varchar(255) comment '标签',
    picture_url varchar(255) comment'封面url',
    summary varchar(1000) comment '简介',
    is_recommended tinyint(1) not null DEFAULT 0 COMMENT'1代表是推荐作品，0代表不是推荐作品',
    importer varchar(25) comment'导入人',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '视频合集表';
#加简介加封面

create table video_Collection(
    video_id int UNSIGNED not null comment '视频id',
    collection_id int UNSIGNED not null comment '合集id',
    sequence int UNSIGNED not null comment '合集顺序',
    primary key (video_id,collection_id)
)comment '视频和和合集关系表';

create table protocol (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '协议id',
    name VARCHAR(100) NOT NULL comment '协议名',
    content varchar(255) not null comment '协议文件url',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '协议';

/*
可以存html，这需要附带连接表，也可以如上存url，内容存在服务器上
create table protocol_content(
    protocol_id int not null comment '协议id',
    message varchar(5000) comment '协议html内容'
)comment '协议html内容';

create table link(
    id INT AUTO_INCREMENT PRIMARY KEY comment '链接id',
    path varchar(255) not null comment '链接url',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '存储链接url表';
*/

create table version(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '版本id',
    name VARCHAR(100) NOT NULL comment '版本号',
    release_date datetime comment '版本发布日期',
    content varchar(2000) comment '更新说明',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '版本';

create table character_analysis(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '单字分析id',
    name varchar(10) not null comment '该字名',
    submission_id int UNSIGNED not null comment '作品id',
    score tinyint comment '系统得分',
    evaluation varchar(100) comment '系统评价',
    picture varchar(255) comment '该字图片url',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '作品的单字分析';

create table stroke_analysis(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '笔画分析id',
    character_analysis_id int UNSIGNED not null comment '单字分析id',
    score tinyint comment '系统得分',
    picture varchar(255) comment '该笔画图片url',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '笔画分析';

create table note (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '消息id',
    name VARCHAR(100) NOT NULL comment '消息名',
    type varchar(50) comment '消息类型',
    sender varchar(50) comment '消息发送人',
    association_id int UNSIGNED comment '相关联的id，例如作业id竞赛id',
    target varchar(10) comment '只有系统消息和竞赛消息用到此处，系统消息判断有学生，教师，全体，竞赛消息判断有小学，初中，全体',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '消息';

create table note_content(
    note_id int UNSIGNED primary key comment '消息id',
    message varchar(2000) comment '消息内容'
)comment '消息内容';

create table student_note_receive(
    note_id int UNSIGNED not null comment '消息id',
    student_id int UNSIGNED not null comment '学生id',
    primary key(student_id,note_id)
)comment '学生消息关系表';

create table klass_note_receive(
    note_id int UNSIGNED not null comment '消息id',
    klass_id int UNSIGNED not null comment '班级id',
    primary key(klass_id,note_id)
)comment '班级消息关系表';

# create table teacher_note_receive(
#     note_id int UNSIGNED not null comment '消息id',
#     teacher_id int UNSIGNED not null comment '教师id',
#     primary key(teacher_id,note_id)
# )comment '教师消息关系表';
#消息关系表已经根据接收人类型分成了三张表，教师消息关系表不要了，因为系统和竞赛直接去消息表里找

create table tea_works_collection(
    teacher_id int UNSIGNED not null comment '教师id',
    submission_id int UNSIGNED not null comment '作品id',
    submission_type enum('作业作品','优秀竞赛作品','优秀作业作品'),
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间',
    primary key (teacher_id,submission_id,submission_type)
)comment '教师的作品收藏';

create table stu_submission_collection(
    student_id int UNSIGNED not null comment '学生id',
    submission_id int UNSIGNED not null comment '作品id',
    type enum('学校练习','自我练习','优秀学校作品','优秀竞赛作品') comment '收藏的类型',
    primary key (student_id,submission_id,type)
)comment '学生的关于作品的收藏';
#
# create table stu_competition_collection(
#     student_id int UNSIGNED not null comment '学生id',
#     submission_id int UNSIGNED not null comment '作品id',
#     primary key (student_id,submission_id)
# )comment '学生的优秀竞赛作品收藏';
#
# create table stu_outstanding_collection(
#     student_id int UNSIGNED not null comment '学生id',
#     submission_id int UNSIGNED not null comment '作品id',
#     primary key (student_id,submission_id)
# )comment '学生的优秀作业作品收藏';
#作品收藏拆成三个表，一个优秀竞赛作品，一个作业作品，一个优秀作业作品
#2024/8/17三表重新合为一个表

create table knowledge_collection(
    student_id int UNSIGNED not null comment '学生id',
    resources_id int UNSIGNED not null comment '书法知识资源id',
    primary key (student_id,resources_id)
)comment '知识收藏';

create table video_knowledge(
    student_id int UNSIGNED not null comment '学生id',
    video_id int UNSIGNED not null comment '视频id',
    primary key (student_id,video_id)
)comment '视频收藏';

create table outstanding_competition(
    submissions_id int UNSIGNED not null primary key comment '竞赛作品id',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '优秀竞赛作品';

create table outstanding_homework(
    submissions_id int UNSIGNED not null primary key comment '作业作品id',
    recommender_id int UNSIGNED not null comment '推荐教师id',
    reviewer_id int UNSIGNED not null comment '评审人id即系统用户id',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '优秀作业作品';

create table unreviewed_outstanding(
    submissions_id int UNSIGNED not null primary key comment '作业作品id',
    recommender_id int UNSIGNED not null comment '推荐教师id',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '未评审的优秀作业作品';












