CREATE TABLE account (
                         id_acc CHAR(20) PRIMARY KEY,
                         email VARCHAR(100),
                         pass VARCHAR(100),
                         role INT DEFAULT (1),
                         statusAcc VARCHAR(50)
);
CREATE TABLE student (
                         id_st CHAR(20) PRIMARY KEY,
                         name VARCHAR(100),
                         birth DATE,
                         describeSt TEXT,
                         id_acc CHAR(20),
                         FOREIGN KEY (id_acc) REFERENCES account(id_acc)
);
CREATE TABLE subject (
                         id_sub CHAR(20) PRIMARY KEY,
                         name VARCHAR(100),
                         level VARCHAR(50),
                         describeSb TEXT,
                         fee DECIMAL(10, 2),
                         statusSub VARCHAR(50)
);

CREATE TABLE tutor (
                       id_tutor CHAR(20) PRIMARY KEY,
                       name VARCHAR(100),
                       email VARCHAR(100) NOT NULL,
                       birth DATE,
                       phone VARCHAR(20),
                       address  VARCHAR(255),
                       specialization  VARCHAR(255),
                       describeTutor TEXT,
                       id_acc CHAR(20),
                       evaluate INT,
                       FOREIGN KEY (id_acc) REFERENCES account(id_acc)
);

CREATE TABLE Course (
                        id_course CHAR(20) PRIMARY KEY,
                        id_sub CHAR(20),
                        id_tutor CHAR(20),
                        timeCourse DATETIME,
                        FOREIGN KEY (id_sub) REFERENCES subject(id_sub),
                        FOREIGN KEY (id_tutor) REFERENCES tutor(id_tutor)
);

CREATE TABLE Registered_Subjects (
                                     id_course CHAR(20),
                                     id_st CHAR(20),
                                     registrationDate DATE,
                                     numberOfLessons INT,
                                     statusRSub VARCHAR(50),
                                     PRIMARY KEY (id_course, id_st),
                                     FOREIGN KEY (id_course) REFERENCES Course(id_course),
                                     FOREIGN KEY (id_st) REFERENCES student(id_st)
);

CREATE TABLE Lesson (
                        id_course CHAR(20),
                        id_st CHAR(20),
                        statusLess VARCHAR(50),
                        TimeLess DATETIME,
                        PRIMARY KEY (id_course, id_st, TimeLess),
                        FOREIGN KEY (id_course) REFERENCES Course(id_course),
                        FOREIGN KEY (id_st) REFERENCES student(id_st)
);
-- Thêm vào bảng account
INSERT INTO account (id_acc, email, pass, role, statusAcc) VALUES
                                                               ('acc001', 'student1@example.com', 'pass1', 1, 'active'),
                                                               ('acc002', 'student2@example.com', 'pass2', 1, 'active'),
                                                               ('acc003', 'student3@example.com', 'pass3', 1, 'inactive'),
                                                               ('acc004', 'tutor1@example.com',   'pass4', 2, 'active'),
                                                               ('acc005', 'tutor2@example.com',   'pass5', 2, 'active'),
                                                               ('acc006', 'tutor3@example.com',   'pass6', 2, 'inactive'),
                                                               ('acc007', 'admin1@example.com',   'pass7', 3, 'active'),
                                                               ('acc008', 'admin2@example.com',   'pass8', 3, 'active'),
                                                               ('acc009', 'tutor4@example.com',   'pass9', 2, 'active'),
                                                               ('acc010', 'student4@example.com', 'pass10', 1, 'inactive');

INSERT INTO student (id_st, name, birth, describeSt, id_acc) VALUES
                                                                 ('st001', 'Nguyen Văn Nghĩa', '2005-01-01', 'Yêu thích Toán', 'acc001'),
                                                                 ('st002', 'Lê Thi Liên',     '2006-03-15', 'Học sinh giỏi Văn', 'acc002'),
                                                                 ('st003', 'Trần Văn Nhớ',   '2004-07-21', 'Thích học nhóm', 'acc003'),
                                                                 ('st004', 'Phạm Thị Dung',   '2005-10-05', 'Năng động, tự tin', 'acc004',
                                                                ('st005', 'Nguyễn Trung Nhân', '2005-01-01', 'Yêu thích Toán', 'acc005'),
                                                                 ('st006', 'Trương Thị Mai',     '2006-03-15', 'Học sinh giỏi Văn', 'acc006'),
                                                                 ('st007', 'Trần Dần',   '2004-07-21', 'Thích học nhóm', 'acc007'),
                                                                 ('st008', 'Lê Trung Dũng',   '2005-10-05', 'Năng động, tự tin', 'acc008'),
                                                                  ('st009', 'Mai Nguyễn',     '2006-03-15', 'Học sinh giỏi Văn', 'acc0069'),
                                                                  ('st010', 'Trương Hoàng Thủ',   '2004-07-21', 'Thích học nhóm', 'acc010'),
                                                                  ('st011', 'Nguyễn Đình Nguyên',   '2005-10-05', 'Năng động, tự tin', 'acc011');


INSERT INTO tutor (id_tutor, name, email, birth, phone, address, specialization, describeTutor, id_acc, evaluate) VALUES
                                                                                                                      ('tut001', 'Nguyễn Tuấn Cảnh', 'nguyencanh@example.com', '1990-01-01', '0901000001', 'Hà Nội', 'Toán', '10 năm kinh nghiệm dạy Toán', 'acc001', 5),
                                                                                                                      ('tut002', 'Tran Thị Mai', 'tranmai@example.com', '1988-05-12', '0901000002', 'HCM', 'Tiếng Anh', 'Chuyên luyện giao tiếp', 'acc002', 4),
                                                                                                                      ('tut003', 'Lê Hoàng Minh', 'hoangminh3@example.com', '1992-07-07', '0901000003', 'Đà Nẵng', 'Hóa học', 'GV trường chuyên', 'acc003', 3),
                                                                                                                      ('tut004', 'Phạm Minh Hương', 'minhhuong@example.com', '1991-09-20', '0901000004', 'Huế', 'Toán', 'Nhiệt huyết, vui vẻ', 'acc004', 4),
                                                                                                                    ('tut005', 'Nguyễn Thủ', 'nguyenthu@example.com', '1990-01-01', '0901000001', 'Hà Nội', 'Toán', '10 năm kinh nghiệm dạy Toán', 'acc005', 5),
                                                                                                                      ('tut006', 'Trương Cao Đạt', 'caodat@example.com', '1988-05-12', '0901000002', 'HCM', 'Tiếng Anh', 'Chuyên luyện giao tiếp', 'acc006', 4),
                                                                                                                      ('tut007', 'Đinh Thị Ngọc', 'ngocdinh@example.com', '1992-07-07', '0901000003', 'Đà Nẵng', 'Hóa học', 'GV trường chuyên', 'acc007', 3),
                                                                                                                      ('tut008', 'Lê Nghĩa', 'nghiale@example.com', '1991-09-20', '0901000004', 'Huế', 'Toán', 'Nhiệt huyết, vui vẻ', 'acc008', 4)
                                                                                                                    ('tut009', 'Trần Nguyên Vẹn', 'nguyenven@example.com', '1988-05-12', '0901000002', 'HCM', 'Tiếng Anh', 'Chuyên luyện giao tiếp', 'acc009', 4),
                                                                                                                      ('tut010', 'Mai Hạnh Phúc', 'hanhphuc@example.com', '1992-07-07', '0901000003', 'Đà Nẵng', 'Hóa học', 'GV trường chuyên', 'acc010', 3),
                                                                                                                      ('tut011', 'Mai Thắng', 'thang@example.com', '1991-09-20', '0901000004', 'Huế', 'Toán', 'Nhiệt huyết, vui vẻ', 'acc011', 4);


INSERT INTO subject (id_sub, name, level, describeSb, fee, statusSub) VALUES
                                                                          ('sub001', 'Toán', 'Lớp 10', 'Học Toán nâng cao lớp 10', 200000.00, 'active'),
                                                                          ('sub002', 'Tiếng Anh', 'Giao tiếp', 'Tiếng Anh giao tiếp cơ bản', 180000.00, 'active'),
                                                                          ('sub003', 'Hóa học', 'Cơ bản', 'Học hóa cơ bản lớp 10', 190000.00, 'inactive'),
                                                                            ('sub004', 'Vật lý', 'Lớp 10', 'Học Vật lý cao lớp 12', 200000.00, 'active'),
                                                                          ('sub005', 'Ngữ văn', 'Nâng cao', 'Học Ngữ văn nâng cao lớp 11', 200000.00, 'active'),
                                                                          ('sub006', 'Sinh học', 'Cơ bản', 'Học hóa cơ bản lớp 10', 190000.00, 'inactive'),
                                                                        ('sub007', 'Toán', 'Lớp 5', 'Học Toán nâng cao lớp 5', 200000.00, 'active'),
                                                                          ('sub008', 'Tiếng Anh', 'Giao tiếp', 'Tiếng Anh giao tiếp nâng cao lớp 5', 180000.00, 'active'),
                                                                          ('sub009', 'Toán', 'Cơ bản', 'Toán cơ bản lớp 6', 190000.00, 'inactive'),
                                                                        ('sub010', 'Vật lý', 'Lớp 11', 'Học Vật lý nâng cao lớp 8', 200000.00, 'active'),
                                                                          ('sub011', 'Hóa học', 'Cơ bản', 'Học hóa học cơ bản lớp 9', 180000.00, 'active'),
                                                                          ('sub012', 'Sinh học', 'Cơ bản', 'Sinh học cơ bản lớp 8', 190000.00, 'inactive'),
                                                                          ('sub013', 'Toán', 'Lớp 10', 'Học Toán nâng cao lớp 10', 200000.00, 'active'),
                                                                          ('sub014', 'Tiếng Anh', 'Cơ bản', 'Tiếng Anh cơ bản lớp 10', 180000.00, 'active'),
                                                                          ('sub015', 'Hóa học', 'Cơ bản', 'Học hóa cơ bản lớp 7', 190000.00, 'inactive');


INSERT INTO Course (id_course, id_sub, id_tutor, timeCourse) VALUES
                                                                 ('course001', 'sub001', 'tut001', '2025-05-01 08:00:00'),
                                                                 ('course002', 'sub002', 'tut002', '2025-05-02 09:00:00'),
                                                                 ('course003', 'sub003', 'tut003', '2025-05-03 10:00:00');


INSERT INTO Registered_Subjects (id_course, id_st, registrationDate, numberOfLessons, statusRSub) VALUES
                                                                                                      ('course001', 'st001', '2025-05-01', 10, 'active'),
                                                                                                      ('course002', 'st002', '2025-05-02', 8, 'active'),
                                                                                                      ('course001', 'st003', '2025-05-03', 6, 'inactive'),
                                                                                                      ('course003', 'st004', '2025-05-04', 12, 'active');


INSERT INTO Lesson (id_course, id_st, statusLess, TimeLess) VALUES
                                                                ('course001', 'st001', 'completed', '2025-05-01 08:00:00'),
                                                                ('course001', 'st001', 'completed', '2025-05-03 08:00:00'),
                                                                ('course002', 'st002', 'completed', '2025-05-02 09:00:00'),
                                                                ('course002', 'st002', 'absent',    '2025-05-04 09:00:00'),
                                                                ('course001', 'st003', 'scheduled', '2025-05-05 08:00:00'),
                                                                ('course003', 'st004', 'completed', '2025-05-04 10:00:00'),
                                                                ('course003', 'st004', 'scheduled', '2025-05-06 10:00:00');

