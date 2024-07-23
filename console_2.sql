use calligraphy_evaluation_system;
#INT：如无特殊需要，存放整型数字使用UNSIGNED INT型，整型字段后的数字代表显示长度。比如 id int(11) NOT NULL
#DATETIME：所有需要精确到时间(时分秒)的字段均使用DATETIME,不要使用TIMESTAMP类型。----------已改
#对于精确浮点型数据存储，需要使用DECIMAL，严禁使用FLOAT和DOUBLE。
#如无特殊需要，字段建议使用NOT NULL属性，可用默认值代替NULL。
#自增字段类型必须是整型且必须为UNSIGNED，推荐类型为INT或BIGINT，并且自增字段必须是主键或者主键的一部分。------已解决
#enum看看能不能都拆分成多个表-------已解决
#前面全部改用偏旁部首，数据库中叫做部首eccentricity

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
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '用户组';
#给用户组也增加逻辑删除
ALTER TABLE user_group
ADD COLUMN delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）';

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



CREATE TABLE region (
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '区域id',
  name VARCHAR(25) NOT NULL comment '区域名',
  parent_id INT UNSIGNED DEFAULT NULL comment '上一级id',
  level tinyINT NOT NULL comment '等级',
  updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '区域';

CREATE TABLE grade(
  id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '学制年级id',
  name VARCHAR(25) NOT NULL comment '学制年级名',
  parent_id INT UNSIGNED DEFAULT NULL comment '上一级id',
  level tinyint NOT NULL comment'等级',
  updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '学制年级';
INSERT INTO grade (name, parent_id, level) VALUES
('六三制小学', NULL, 1),
('一年级', 1, 2),
('二年级', 1, 2),
('三年级', 1, 2),
('四年级', 1, 2),
('五年级', 1, 2),
('六年级', 1, 2),
('六三制初中', NULL, 1),
('初一', 8, 2),
('初二', 8, 2),
('初三', 8, 2),
('五四制小学', NULL, 1),
('一年级', 12, 2),
('二年级', 12, 2),
('三年级', 12, 2),
('四年级', 12, 2),
('五年级', 12, 2),
('五四制初中', NULL, 1),
('初一', 18, 2),
('初二', 18, 2),
('初三', 18, 2),
('初四', 18, 2);

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
ALTER TABLE school
ADD COLUMN delete_flag TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）';
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
    year varchar(4) not null comment '入学年份',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '班级';
#班级表加个入校年份，创建时间作为不了入校年份，所以下面的没有执行，上面表已修改
#alter table klass add admission_year varchar(4) not null comment '入学年份';

create table student(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '学生id',
    name varchar(25) not null comment '学生名',
    student_number varchar(50) not null comment '学籍号',
    password VARCHAR(100) NOT NULL comment'密码',
    klass_id int UNSIGNED not null comment '所属班级id',
    phone varchar(25) comment'电话',
    email varchar(50) comment '邮箱',
    region_id int UNSIGNED comment '所在区域id',
    id_number varchar(50) comment'身份证号',
    gender ENUM('男', '女'),
    picture_url varchar(255) comment'头像url',
    delete_flag tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '学生';

create table teacher(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '教师id',
    name varchar(25) not null comment '教师名',
    workno VARCHAR(50) NOT NULL comment'工号',
    password VARCHAR(100) NOT NULL comment'密码',
    id_number varchar(50) comment'身份证号',
    gender ENUM('男', '女') comment '性别',
    phone varchar(25) comment'电话',
    email varchar(50) comment '邮箱',
    school_id int UNSIGNED not null comment '所属学校id',
    region_id int UNSIGNED comment '所在区域id',
    picture_url varchar(255) comment'头像url',
    delete_flag tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除（0 未删除、1 删除）',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '教师';
# alter table teacher
#     add column school_id int UNSIGNED not null comment '所属学校id';


#教师和系统模板再来一个关系表，用于表示该教师常用的系统模板有哪些，已成为下表
create table teacher_system_template(
    teacher_id int UNSIGNED not null comment '教师id',
    system_template_id int UNSIGNED not null comment '系统模板id',
    primary key (teacher_id,system_template_id)
);


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
    detail_type enum ('偏旁','结构','笔画'),
    font_id int UNSIGNED not null comment '字体id',
    word_count int not null comment '字数',
    requirements varchar(500) comment '作业要求',
    deadline datetime not null comment '截止时间',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '作业';
#front改成font，上表已改

create table homework_submission(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '作业作品id',
    system_score tinyint comment '系统评分',
    system_feedback varchar(255) comment '系统评语',
    teacher_score tinyint comment '教师得分',
    teacher_feedback varchar(255) comment '教师评语',
    content varchar(255) comment'作业作品内容url',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '作品作业';
#作业id改成作业作品id，上表已更改

create table student_do_homework(
    homework_id int UNSIGNED not null comment '作业id',
    student_id int UNSIGNED not null comment '学生id',
    homework_submission int UNSIGNED not null comment '作业作品id',
    primary key (student_id,homework_id)
)comment '学生作业表';
create table klass_homework(
    homework_id int UNSIGNED not null comment '作业id',
    klass_id int UNSIGNED not null comment '班级id',
    primary key (klass_id,homework_id)
)comment '班级作业表';
#student_id 要和消息一样改成学生和班级的id，然后下面来个类型,已分成两表
#作业作品id去掉，在作业作品里加一个作业id，上两表已修改，，，，，上面两表的结果是已经思考过了的
#下面主要是为了查询，查询的同时还需要往学生作业表中添加，表示该学生有作业了

create table custom_template(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '模板id',
    name varchar(50) not null comment '模板名',
    type enum('综合','专项','字帖'),
    detail_type enum ('偏旁','结构','笔画'),
    font_id int UNSIGNED not null comment '字体id',
    content varchar(255) not null comment'作业内容url',
    creator_id int UNSIGNED not null comment'创作人id',
    difficulty enum('1','2','3','4','5') not null comment '难度',
    word_count int comment '字数',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '自定义模板';
#已更改上表font

create table system_template(
    id int AUTO_INCREMENT PRIMARY KEY comment '模板id',
    name varchar(50) not null comment '模板名',
    type enum('综合','专项','字帖'),
    detail_type enum ('偏旁','结构','笔画'),
    font_id int not null comment '字体id',
    content varchar(255) not null comment'作业内容url',
    creator_id int not null comment'创作人id',
    difficulty enum('1','2','3','4','5') not null comment '难度',
    word_count int comment '字数',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '系统模板';
#已更改上表font，字体id可能要考虑要不要去掉，因为字帖模板，详细类型也要改，笔画去掉，未操作删改

create table teacher_homework(
    teacher_id int UNSIGNED not null comment '教师id',
    homework_id int UNSIGNED not null comment '作业id',
    template_id int UNSIGNED not null comment '模板id',
    template_type enum('自定义','系统') comment '模板类型',
    target ENUM('个人', '集体') comment '发布对象',
    difficulty enum('1','2','3','4','5') not null comment '难度',
    primary key (teacher_id,homework_id)
)comment '教师对作业的布置，同时记录是否使用了模板';
#教师、模板、作业之间有联系了，，，如果是通过已有作业来创建的，就把已有作业的东西复制到模板里成为自定义模板
create table student_create_homework(
    student_id int UNSIGNED not null comment '学生id',
    homework_id int UNSIGNED not null comment '作业id',
    template_id int UNSIGNED not null comment '模板id',
    template_type enum('自定义','系统') comment '模板类型',
    primary key (student_id,homework_id)
)comment '学生自我练习的作业';

create table font(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '字体id',
    name varchar(25) not null comment '字体名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '字体';
#字体已改成font

create table stroke (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '笔画id',
    name varchar(25) not null comment '笔画名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '笔画';

create table eccentricity (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '偏旁id',
    name varchar(25) not null comment '偏旁名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '偏旁';

create table structure (
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '结构id',
    name varchar(25) not null comment '结构名',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '结构';

create table template_word(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '模板字id',
    name varchar(15) not null comment '模板字名',
    calligrapher varchar(15) not null comment '书写的书法家名',
    structure_id int UNSIGNED not null comment '结构id',
    font_id int UNSIGNED not null comment '字体id',
    eccentricity_id int UNSIGNED not null comment '偏旁id',
    content varchar(255) comment'模板字图片url',
    importer varchar(25) comment'导入人',
    grade_id int UNSIGNED not null comment '学制年级id',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '模板字';
#已更改上表font

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
alter table copybook add grade_system_id int  comment '学制年级id';
#已更改上表font

create table sample_word(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '样本字id',
    name varchar(15) not null comment '样本字名',
    structure_id int UNSIGNED not null comment '结构id',
    font_id int UNSIGNED not null comment '字体id',
    eccentricity_id int UNSIGNED not null comment '偏旁id',
    content varchar(255) comment'字帖图片url',
    source varchar(50) comment '来源',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '样本字';
#已更改上表font

create table feedback(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '反馈id',
    provider_id int UNSIGNED not null comment '反馈人id',
    provider_type enum('教师','学生','管理员') not null comment '反馈人类型',
    type varchar(50) not null comment '反馈性质',
    state varchar(25) not null comment '反馈状态',
    provider_phone varchar(20) comment '反馈人电话',
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
    target enum('学生与教师','管理员','全体') comment '发布对象',
    state varchar(25) not null comment '公告状态',
    type varchar(30) not null comment '公告类型',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '公告';

create table announcement_content(
    announcement_id int UNSIGNED not null primary key comment '公告id',
    message varchar(3000) not null comment '公告内容',
    file varchar(555) comment '公告附件url'
)comment '公告内容';

#对话记录是暂时的，即用完就删，故存html，，，，，客服对话记录删了，不要了，不要客服了
create table chat(
    id bigint unsigned AUTO_INCREMENT PRIMARY KEY comment '对话记录id',
    sender_id int not null comment '发送人id',
    sender_type enum('教师','学生','管理员'),
    receiver_id int not null comment '接收人id',
    state varchar(25) not null comment '状态',
    content varchar(2000) comment '对话记录内容html',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '客服对话记录';

#此处把属性question和answer改为q和a
create table question(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '常见问题id',
    q  varchar(200) not null comment '问题',
    a varchar(400) not null comment '答案'
)comment '常见问题';

create table competition(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '竞赛id',
    name varchar(50) not null comment '竞赛名称',
    registration_start_time datetime not null comment '开始报名时间',
    registration_end_time datetime not null comment '结束报名时间',
    competition_start_time datetime not null comment '开始竞赛时间',
    competition_end_time datetime not null comment '结束竞赛时间',
    review_start_time datetime not null comment '开始评阅时间',
    review_end_time datetime not null comment '结束评阅时间',
    state varchar(50) comment '状态',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '竞赛';

create table competition_requirements(
    competition_id int UNSIGNED not null primary key comment '竞赛id',
    requirements varchar(2000) not null comment '竞赛要求',
    picture varchar(255) comment '宣传图url'
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
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '组别';

create table division_requirements(
    division_id int UNSIGNED not null primary key comment '组别id',
    requirements varchar(2000) not null comment '组别要求'
)comment '组别要求';

create table competition_submissions(
    id int UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '竞赛作品id',
    competition_id int UNSIGNED not null comment '竞赛id',
    division_id int UNSIGNED not null comment '组别id',
    author_id int UNSIGNED not null comment '作者id',
    initial_score tinyint comment '初级评分',
    initial_evaluation varchar(255) comment '初级评价',
    system_score tinyint comment '系统评分',
    system_evaluation varchar(255)comment '系统评价',
    average_final_score decimal(5,3) comment '最终评分均分',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '竞赛作品';

create table participant(
    division_id int UNSIGNED comment '组别id',
    student_id int UNSIGNED comment '学生id',
    submission_id int UNSIGNED comment '学生作品id',
    primary key (division_id,student_id)
)comment '竞赛参赛人员表';

create table reviewers(
    division_id int UNSIGNED comment '组别id',
    teacher_id int UNSIGNED comment '评阅教师id即系统用户id',
    primary key (division_id,teacher_id)
)comment '评阅教师表';

create table initial_review(
    teacher_id int UNSIGNED comment '评阅教师id即系统用户id',
    submission_id int UNSIGNED comment '学生作品id',
    primary key (teacher_id,submission_id)
)comment '初级评阅记录';

create table final_review(
    teacher_id int UNSIGNED comment '评阅教师id即系统用户id',
    submission_id int UNSIGNED comment '学生作品id',
    score tinyint comment '该老师评此作品评分',
    primary key (teacher_id,submission_id)
)comment '最终评阅记录';

create table initial_rank(
    submission_id int UNSIGNED primary key comment '作品id',
    competition_id int UNSIGNED not null comment'竞赛id',
    rk int comment '排名',
    score tinyint not null comment '初级评分得分',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '初级评分排名';

create table final_rank(
    submission_id int UNSIGNED primary key comment '作品id',
    competition_id int UNSIGNED not null comment'竞赛id',
    rk int comment '排名',
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

create table calligraphy_resources(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '书法知识类型id',
    name VARCHAR(100) NOT NULL comment '书法知识类型名',
    first_type_id int UNSIGNED not null comment '第一类型id',
    second_type_id int UNSIGNED not null comment '第二类型id',
    custom_type varchar(255) comment '自定义类型',
    content varchar(255) not null comment '资源文件url',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '书法知识资源';
drop table calligraphy_resources;#删了是因为分成下面两个表了

create table article_resources(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '文章id',
    name VARCHAR(100) NOT NULL comment '文章名',
    first_type_id int UNSIGNED not null comment '第一类型id',
    second_type_id int UNSIGNED not null comment '第二类型id',
    custom_type varchar(255) comment '自定义类型',
    content varchar(255) not null comment '资源文件url',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '书法知识文章表';

create table video_resources(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '视频id',
    name VARCHAR(100) NOT NULL comment '视频名',
    first_type_id int UNSIGNED not null comment '第一类型id',
    second_type_id int UNSIGNED not null comment '第二类型id',
    custom_type varchar(255) comment '自定义类型',
    collection_id int UNSIGNED comment '所属合集id',
    content varchar(255) not null comment '资源文件url',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '书法知识视频表';

create table video_collection(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '合集id',
    name VARCHAR(100) NOT NULL comment '合集名',
    first_type_id int UNSIGNED not null comment '第一类型id',
    second_type_id int UNSIGNED not null comment '第二类型id',
    custom_type varchar(255) comment '自定义类型',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '视频合集表';
#加简介加封面

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
    submissions_id int UNSIGNED not null comment '作品id',
    score tinyint comment '系统得分',
    evaluation varchar(100) comment '系统评价',
    picture varchar(255) comment '该字图片url',
    location_x int comment '在整体图片上的位置x',
    location_y int comment '在整体图片上的位置y',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '作品的单字分析';

create table stroke_analysis(
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY comment '笔画分析id',
    submissions_id int UNSIGNED not null comment '作品id',
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

create table teacher_note_receive(
    note_id int UNSIGNED not null comment '消息id',
    teacher_id int UNSIGNED not null comment '教师id',
    primary key(teacher_id,note_id)
)comment '教师消息关系表';
#消息关系表已经根据接收人类型分成了三张表

create table tea_works_collection(
    teacher_id int UNSIGNED not null comment '教师id',
    submission_id int UNSIGNED not null comment '作品id',
    submission_type enum('作业作品','优秀竞赛作品','优秀作业作品'),
    primary key (teacher_id,submission_id,submission_type)
)comment '教师的作品收藏';

create table stu_homework_collection(
    student_id int UNSIGNED not null comment '学生id',
    submission_id int UNSIGNED not null comment '作品id',
    primary key (student_id,submission_id)
)comment '学生的作业作品收藏';

create table stu_competition_collection(
    student_id int UNSIGNED not null comment '学生id',
    submission_id int UNSIGNED not null comment '作品id',
    primary key (student_id,submission_id)
)comment '学生的优秀竞赛作品收藏';

create table stu_outstanding_collection(
    student_id int UNSIGNED not null comment '学生id',
    submission_id int UNSIGNED not null comment '作品id',
    primary key (student_id,submission_id)
)comment '学生的优秀作业作品收藏';
#作品收藏拆成三个表，一个优秀竞赛作品，一个作业作品，一个优秀作业作品

create table knowledge_collection(
    student_id int UNSIGNED not null comment '学生id',
    resources_id int UNSIGNED not null comment '书法知识资源id',
    primary key (student_id,resources_id)
)comment '知识收藏';

create table outstanding_competition(
    submissions_id int UNSIGNED primary key comment '竞赛作品id',
    created_time datetime DEFAULT CURRENT_TIMESTAMP comment'创建时间'
)comment '优秀竞赛作品';

create table outstanding_homework(
    submissions_id int UNSIGNED primary key comment '作业作品id',
    state varchar(50) comment '状态',
    recommender_id int UNSIGNED not null comment '推荐教师id',
    reviewer_id int UNSIGNED not null comment '评审人id即系统用户id',
    updated_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  comment'更新时间'
)comment '优秀作业作品';













