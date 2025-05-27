-- Chức năng của từng bảng

-- Bảng account
-- Chức năng: Lưu trữ thông tin tài khoản người dùng trong hệ thống, bao gồm cả học sinh, gia sư và admin.
-- Vai trò:
-- Quản lý thông tin đăng nhập (email, mật khẩu).
-- Phân quyền người dùng qua role (1: student, 2: tutor, 3: admin).
-- Theo dõi trạng thái tài khoản (status_acc: active/inactive).

-- Bảng student
-- Chức năng: Lưu trữ thông tin cá nhân của học sinh.
-- Vai trò:
-- Quản lý thông tin cơ bản (tên, ngày sinh, mô tả).
-- Liên kết với tài khoản qua id_acc để xác định học sinh nào có tài khoản trong hệ thống.

-- Bảng subject
-- Chức năng: Lưu trữ thông tin về các môn học mà hệ thống cung cấp.
-- Vai trò:
-- Quản lý danh sách môn học (tên, cấp độ, mô tả, phí).
-- Theo dõi trạng thái môn học (status_sub: active/inactive) để quyết định môn nào đang được cung cấp.

-- Bảng tutor
-- Chức năng: Lưu trữ thông tin cá nhân và chuyên môn của gia sư.
-- Vai trò:
-- Quản lý thông tin gia sư (tên, email, ngày sinh, số điện thoại, địa chỉ, chuyên môn, mô tả).
-- Liên kết với tài khoản qua id_acc.
-- Lưu trữ đánh giá (evaluate) để xếp hạng chất lượng gia sư.

-- Bảng course
-- Chức năng: Lưu trữ thông tin về các khóa học được tổ chức.
-- Vai trò:
-- Quản lý khóa học cụ thể (môn học, gia sư, thời gian bắt đầu).
-- Liên kết với subject (môn học) và tutor (gia sư) để xác định khóa học thuộc môn nào và do ai phụ trách.

-- Bảng registered_subjects
-- Chức năng: Lưu trữ thông tin đăng ký khóa học của học sinh.
-- Vai trò:
-- Theo dõi học sinh đăng ký khóa học nào (id_course, id_st).
-- Quản lý ngày đăng ký, số buổi học, và trạng thái đăng ký (status_rsub: registered/completed/cancelled).

-- Bảng lesson
-- Chức năng: Lưu trữ thông tin chi tiết về từng buổi học trong khóa học mà học sinh tham gia.
-- Vai trò:
-- Quản lý trạng thái buổi học (status_less: completed/absent/scheduled).
-- Ghi nhận thời gian cụ thể của từng buổi (time_less).
-- Liên kết với course và student để biết buổi học thuộc khóa nào và học sinh nào tham gia.

-- Tổng kết
-- account: Quản lý tài khoản và phân quyền người dùng.
-- student: Lưu thông tin học sinh.
-- tutor: Lưu thông tin gia sư.
-- subject: Quản lý danh sách môn học.
-- course: Quản lý khóa học (môn học + gia sư + thời gian).
-- registered_subjects: Theo dõi học sinh đăng ký khóa học.
-- lesson: Quản lý chi tiết các buổi học trong khóa học.

CREATE TABLE account (
                         id_acc CHAR(20) PRIMARY KEY,
                         email VARCHAR(100) NOT NULL,
                         pass VARCHAR(100) NOT NULL,
                         role INT DEFAULT 1 CHECK (role IN (1, 2, 3)), -- 1: student, 2: tutor, 3: admin
                         status_acc VARCHAR(50) NOT NULL CHECK (status_acc IN ('active', 'inactive'))
);

CREATE TABLE student (
                         id_st CHAR(20) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         birth DATE NOT NULL,
                         describe_st TEXT,
                         id_acc CHAR(20),
                         FOREIGN KEY (id_acc) REFERENCES account(id_acc)
);

CREATE TABLE subject (
                         id_sub CHAR(20) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         level VARCHAR(50) NOT NULL,
                         describe_sb TEXT,
                         fee DECIMAL(10, 2) NOT NULL,
                         status_sub VARCHAR(50) NOT NULL CHECK (status_sub IN ('active', 'inactive'))
);

CREATE TABLE tutor (
                       id_tutor CHAR(20) PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       birth DATE NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       specialization VARCHAR(255) NOT NULL,
                       describe_tutor TEXT,
                       cccd INT(12) NOT NULL ,
                       bank_code INT(15) NOT NULL,
                       bank_name VARCHAR(255) NOT NULL,
                       id_acc CHAR(20),
                       evaluate INT CHECK (evaluate BETWEEN 1 AND 5),
                       FOREIGN KEY (id_acc) REFERENCES account(id_acc)
);

CREATE TABLE course (
                        id_course CHAR(20) PRIMARY KEY,
                        id_sub CHAR(20),
                        id_tutor CHAR(20),
                        time_course DATETIME NOT NULL,
                        FOREIGN KEY (id_sub) REFERENCES subject(id_sub),
                        FOREIGN KEY (id_tutor) REFERENCES tutor(id_tutor)
);

CREATE TABLE registered_subjects (
                                     id_course CHAR(20),
                                     id_st CHAR(20),
                                     registration_date DATE NOT NULL,
                                     number_of_lessons INT NOT NULL,
                                     status_rsub VARCHAR(50) NOT NULL CHECK (status_rsub IN ('registered', 'completed', 'cancelled', 'pending_payment')),
                                     PRIMARY KEY (id_course, id_st),
                                     FOREIGN KEY (id_course) REFERENCES course(id_course),
                                     FOREIGN KEY (id_st) REFERENCES student(id_st)
);

CREATE TABLE lesson (
                        id_course CHAR(20),
                        id_st CHAR(20),
                        status_less VARCHAR(50) NOT NULL CHECK (status_less IN ('completed', 'absent', 'scheduled')),
                        time_less DATETIME NOT NULL,
                        PRIMARY KEY (id_course, id_st, time_less),
                        FOREIGN KEY (id_course) REFERENCES course(id_course),
                        FOREIGN KEY (id_st) REFERENCES student(id_st)
);

INSERT INTO account (id_acc, email, pass, role, status_acc) VALUES
                                                                ('acc001', 'student1@example.com', 'pass1', 1, 'active'),
                                                                ('acc002', 'student2@example.com', 'pass2', 1, 'active'),
                                                                ('acc003', 'student3@example.com', 'pass3', 1, 'inactive'),
                                                                ('acc004', 'tutor1@example.com', 'pass4', 2, 'active'),
                                                                ('acc005', 'tutor2@example.com', 'pass5', 2, 'active'),
                                                                ('acc006', 'tutor3@example.com', 'pass6', 2, 'inactive'),
                                                                ('acc007', 'admin1@example.com', 'pass7', 3, 'active'),
                                                                ('acc008', 'admin2@example.com', 'pass8', 3, 'active'),
                                                                ('acc009', 'tutor4@example.com', 'pass9', 2, 'active'),
                                                                ('acc010', 'student4@example.com', 'pass10', 1, 'inactive'),
                                                                ('acc011', 'student5@example.com', 'pass11', 1, 'active'),
                                                                ('acc012', 'student6@example.com', 'pass12', 1, 'active'),
                                                                ('acc013', 'student7@example.com', 'pass13', 1, 'active'),
                                                                ('acc014', 'student8@example.com', 'pass14', 1, 'active'),
                                                                ('acc015', 'tutor5@example.com', 'pass15', 2, 'active'),
                                                                ('acc016', 'tutor6@example.com', 'pass16', 2, 'active'),
                                                                ('acc017', 'tutor7@example.com', 'pass17', 2, 'active'),
                                                                ('acc018', 'tutor8@example.com', 'pass18', 2, 'active'),
                                                                ('acc019', 'tutor9@example.com', 'pass19', 2, 'active'),
                                                                ('acc020', 'tutor10@example.com', 'pass20', 2, 'active');

INSERT INTO student (id_st, name, birth, describe_st, id_acc) VALUES
                                                                  ('st001', 'Nguyen Van Nghia', '2005-01-01', 'Yeu thich Toan', 'acc001'),
                                                                  ('st002', 'Le Thi Lien', '2006-03-15', 'Hoc sinh gioi Van', 'acc002'),
                                                                  ('st003', 'Tran Van Nho', '2004-07-21', 'Thich hoc nhom', 'acc003'),
                                                                  ('st004', 'Pham Thi Dung', '2005-10-05', 'Nang dong, tu tin', 'acc010'),
                                                                  ('st005', 'Nguyen Trung Nhan', '2005-01-01', 'Yeu thich Toan', 'acc011'),
                                                                  ('st006', 'Truong Thi Mai', '2006-03-15', 'Hoc sinh gioi Van', 'acc012'),
                                                                  ('st007', 'Tran Dan', '2004-07-21', 'Thich hoc nhom', 'acc013'),
                                                                  ('st008', 'Le Trung Dung', '2005-10-05', 'Nang dong, tu tin', 'acc014');
INSERT INTO tutor (id_tutor, name, email, birth, phone, address, specialization, describe_tutor, cccd, bank_code, bank_name, id_acc, evaluate) VALUES
                                                                                                                                                   ('tut001', 'Nguyen Tuan Canh', 'tut1@example.com', '1990-01-01', '0901000001', 'Ha Noi', 'Toan', '10 nam kinh nghiem day Toan',740024681397,34568947,"bidv", 'acc004', 5),
                                                                                                                                                   ('tut002', 'Tran Thi Mai', 'tut2@example.com', '1988-05-12', '0901000002', 'HCM', 'Tieng Anh', 'Chuyen luyen giao tiep', 740024681312, 24025002,"sacombank",'acc005', 4),
                                                                                                                                                   ('tut003', 'Le Hoang Minh', 'tut3@example.com', '1992-07-07', '0901000003', 'Da Nang', 'Hoa hoc', 'GV truong chuyen', 740024681356, 29032002,"techcombank",'acc006', 3),
                                                                                                                                                   ('tut004', 'Pham Minh Huong', 'tut4@example.com', '1991-09-20', '0901000004', 'Hue', 'Toan', 'Nhiet huyet, vui ve', 740024681332, 123456789,"mb",'acc009', 4),
                                                                                                                                                   ('tut005', 'Nguyen Thu', 'tut5@example.com', '1990-01-01', '0901000001', 'Ha Noi', 'Toan', '10 nam kinh nghiem day Toan', 740024681374, 68686868,"tp bank",'acc015', 5),
                                                                                                                                                   ('tut006', 'Truong Cao Dat', 'tut6@example.com', '1988-05-12', '0901000002', 'HCM', 'Tieng Anh', 'Chuyen luyen giao tiep', 740024681385, 39393939,"agribank",'acc016', 4),
                                                                                                                                                   ('tut007', 'Dinh Thi Ngoc', 'tut7@example.com', '1992-07-07', '0901000003', 'Da Nang', 'Hoa hoc', 'GV truong chuyen', 740024681318, 147852369,"bidv",'acc017', 3),
                                                                                                                                                   ('tut008', 'Le Nghia', 'tut8@example.com', '1991-09-20', '0901000004', 'Hue', 'Toan', 'Nhiet huyet, vui ve', 740024681370, 369258147,"mb",'acc018', 4),
                                                                                                                                                   ('tut009', 'Tran Nguyen Ven', 'tut9@example.com', '1988-05-12', '0901000002', 'HCM', 'Tieng Anh', 'Chuyen luyen giao tiep', 740024681329, 26032002,"sacombank",'acc019', 4),
                                                                                                                                                   ('tut010', 'Mai Hanh Phuc', 'tut10@example.com', '1992-07-07', '0901000003', 'Da Nang', 'Hoa hoc', 'GV truong chuyen', 740024681317, 07042004,"tp bank",'acc020', 3);
INSERT INTO subject (id_sub, name, level, describe_sb, fee, status_sub) VALUES
                                                                            ('sub001', 'Toan', 'Lop 10', 'Hoc Toan nang cao lop 10', 200000.00, 'active'),
                                                                            ('sub002', 'Tieng Anh', 'Giao tiep', 'Tieng Anh giao tiep co ban', 180000.00, 'active'),
                                                                            ('sub003', 'Hoa hoc', 'Co ban', 'Hoc hoa co ban lop 10', 190000.00, 'inactive'),
                                                                            ('sub004', 'Vat ly', 'Lop 10', 'Hoc Vat ly cao lop 12', 200000.00, 'active'),
                                                                            ('sub005', 'Ngu van', 'Nang cao', 'Hoc Ngu van nang cao lop 11', 200000.00, 'active'),
                                                                            ('sub006', 'Sinh hoc', 'Co ban', 'Hoc Sinh hoc co ban lop 10', 190000.00, 'inactive'), -- Sửa mô tả
                                                                            ('sub007', 'Toan', 'Lop 5', 'Hoc Toan nang cao lop 5', 200000.00, 'active'),
                                                                            ('sub008', 'Tieng Anh', 'Giao tiep', 'Tieng Anh giao tiep nang cao lop 5', 180000.00, 'active'),
                                                                            ('sub009', 'Toan', 'Co ban', 'Toan co ban lop 6', 190000.00, 'inactive'),
                                                                            ('sub010', 'Vat ly', 'Lop 11', 'Hoc Vat ly nang cao lop 8', 200000.00, 'active'),
                                                                            ('sub011', 'Hoa hoc', 'Co ban', 'Hoc hoa hoc co ban lop 9', 180000.00, 'active'),
                                                                            ('sub012', 'Sinh hoc', 'Co ban', 'Sinh hoc co ban lop 8', 190000.00, 'inactive'),
                                                                            ('sub013', 'Toan', 'Lop 10', 'Hoc Toan nang cao lop 10', 200000.00, 'active'),
                                                                            ('sub014', 'Tieng Anh', 'Co ban', 'Tieng Anh co ban lop 10', 180000.00, 'active'),
                                                                            ('sub015', 'Hoa hoc', 'Co ban', 'Hoc hoa co ban lop 7', 190000.00, 'inactive');

INSERT INTO course (id_course, id_sub, id_tutor, time_course) VALUES
                                                                  ('course001', 'sub001', 'tut001', '2025-05-01 08:00:00'),
                                                                  ('course002', 'sub002', 'tut002', '2025-05-02 09:00:00'),
                                                                  ('course003', 'sub003', 'tut003', '2025-05-03 10:00:00'),
                                                                  ('course004', 'sub004', 'tut004', '2025-01-10 08:00:00'),
                                                                  ('course005', 'sub005', 'tut005', '2025-02-15 09:00:00'),
                                                                  ('course006', 'sub001', 'tut001', '2025-03-01 08:00:00'),
                                                                  ('course007', 'sub002', 'tut006', '2025-04-05 09:00:00'),
                                                                  ('course008', 'sub011', 'tut007', '2025-04-10 10:00:00'),
                                                                  ('course009', 'sub014', 'tut009', '2025-05-12 09:00:00');

INSERT INTO registered_subjects (id_course, id_st, registration_date, number_of_lessons, status_rsub) VALUES
                                                                                                          ('course001', 'st001', '2025-04-25', 10, 'completed'),
                                                                                                          ('course002', 'st002', '2025-04-26', 8, 'completed'),
                                                                                                          ('course003', 'st003', '2025-04-27', 5, 'cancelled'),
                                                                                                          ('course001', 'st004', '2025-01-15', 12, 'completed'),
                                                                                                          ('course004', 'st005', '2025-01-20', 10, 'pending_payment'),
                                                                                                          ('course005', 'st006', '2025-02-10', 8, 'completed'),
                                                                                                          ('course006', 'st007', '2025-03-05', 15, 'completed'),
                                                                                                          ('course007', 'st008', '2025-04-01', 10, 'pending_payment'),
                                                                                                          ('course008', 'st001', '2025-04-15', 6, 'completed'),
                                                                                                          ('course009', 'st002', '2025-05-01', 8, 'completed'),
                                                                                                          ('course004', 'st003', '2025-01-25', 10, 'completed'),
                                                                                                          ('course005', 'st004', '2025-02-20', 8, 'cancelled'),
                                                                                                          ('course006', 'st005', '2025-03-10', 12, 'pending_payment'),
                                                                                                          ('course009', 'st006', '2025-05-05', 5, 'pending_payment');

INSERT INTO lesson (id_course, id_st, status_less, time_less) VALUES
-- course001, st001: 10 buổi
('course001', 'st001', 'completed', '2025-05-01 08:00:00'),
('course001', 'st001', 'completed', '2025-05-03 08:00:00'),
('course001', 'st001', 'completed', '2025-05-05 08:00:00'),
('course001', 'st001', 'completed', '2025-05-07 08:00:00'),
('course001', 'st001', 'completed', '2025-05-09 08:00:00'),
('course001', 'st001', 'completed', '2025-05-11 08:00:00'),
('course001', 'st001', 'completed', '2025-05-13 08:00:00'),
('course001', 'st001', 'completed', '2025-05-15 08:00:00'),
('course001', 'st001', 'completed', '2025-05-17 08:00:00'),
('course001', 'st001', 'completed', '2025-05-19 08:00:00'),
-- course002, st002: 8 buổi
('course002', 'st002', 'completed', '2025-05-02 09:00:00'),
('course002', 'st002', 'completed', '2025-05-04 09:00:00'),
('course002', 'st002', 'completed', '2025-05-06 09:00:00'),
('course002', 'st002', 'completed', '2025-05-08 09:00:00'),
('course002', 'st002', 'completed', '2025-05-10 09:00:00'),
('course002', 'st002', 'completed', '2025-05-12 09:00:00'),
('course002', 'st002', 'completed', '2025-05-14 09:00:00'),
('course002', 'st002', 'absent', '2025-05-16 09:00:00'),
-- course001, st004: 12 buổi
('course001', 'st004', 'completed', '2025-01-16 08:00:00'),
('course001', 'st004', 'completed', '2025-01-18 08:00:00'),
('course001', 'st004', 'completed', '2025-01-20 08:00:00'),
('course001', 'st004', 'completed', '2025-01-22 08:00:00'),
('course001', 'st004', 'completed', '2025-01-24 08:00:00'),
('course001', 'st004', 'completed', '2025-01-26 08:00:00'),
('course001', 'st004', 'completed', '2025-01-28 08:00:00'),
('course001', 'st004', 'completed', '2025-01-30 08:00:00'),
('course001', 'st004', 'completed', '2025-02-01 08:00:00'),
('course001', 'st004', 'completed', '2025-02-03 08:00:00'),
('course001', 'st004', 'completed', '2025-02-05 08:00:00'),
('course001', 'st004', 'completed', '2025-02-07 08:00:00'),
-- course005, st006: 8 buổi
('course005', 'st006', 'completed', '2025-02-11 09:00:00'),
('course005', 'st006', 'completed', '2025-02-13 09:00:00'),
('course005', 'st006', 'completed', '2025-02-15 09:00:00'),
('course005', 'st006', 'completed', '2025-02-17 09:00:00'),
('course005', 'st006', 'completed', '2025-02-19 09:00:00'),
('course005', 'st006', 'completed', '2025-02-21 09:00:00'),
('course005', 'st006', 'completed', '2025-02-23 09:00:00'),
('course005', 'st006', 'completed', '2025-02-25 09:00:00'),
-- course006, st007: 15 buổi
('course006', 'st007', 'completed', '2025-03-06 08:00:00'),
('course006', 'st007', 'completed', '2025-03-08 08:00:00'),
('course006', 'st007', 'completed', '2025-03-10 08:00:00'),
('course006', 'st007', 'completed', '2025-03-12 08:00:00'),
('course006', 'st007', 'completed', '2025-03-14 08:00:00'),
('course006', 'st007', 'completed', '2025-03-16 08:00:00'),
('course006', 'st007', 'completed', '2025-03-18 08:00:00'),
('course006', 'st007', 'completed', '2025-03-20 08:00:00'),
('course006', 'st007', 'completed', '2025-03-22 08:00:00'),
('course006', 'st007', 'completed', '2025-03-24 08:00:00'),
('course006', 'st007', 'completed', '2025-03-26 08:00:00'),
('course006', 'st007', 'completed', '2025-03-28 08:00:00'),
('course006', 'st007', 'completed', '2025-03-30 08:00:00'),
('course006', 'st007', 'completed', '2025-04-01 08:00:00'),
('course006', 'st007', 'completed', '2025-04-03 08:00:00'),
-- course008, st001: 6 buổi
('course008', 'st001', 'completed', '2025-04-16 10:00:00'),
('course008', 'st001', 'completed', '2025-04-18 10:00:00'),
('course008', 'st001', 'completed', '2025-04-20 10:00:00'),
('course008', 'st001', 'completed', '2025-04-22 10:00:00'),
('course008', 'st001', 'completed', '2025-04-24 10:00:00'),
('course008', 'st001', 'completed', '2025-04-26 10:00:00'),
-- course009, st002: 8 buổi
('course009', 'st002', 'completed', '2025-05-02 09:00:00'),
('course009', 'st002', 'completed', '2025-05-04 09:00:00'),
('course009', 'st002', 'completed', '2025-05-06 09:00:00'),
('course009', 'st002', 'completed', '2025-05-08 09:00:00'),
('course009', 'st002', 'completed', '2025-05-10 09:00:00'),
('course009', 'st002', 'completed', '2025-05-12 09:00:00'),
('course009', 'st002', 'completed', '2025-05-14 09:00:00'),
('course009', 'st002', 'completed', '2025-05-16 09:00:00'),
-- course004, st003: 10 buổi
('course004', 'st003', 'completed', '2025-01-26 08:00:00'),
('course004', 'st003', 'completed', '2025-01-28 08:00:00'),
('course004', 'st003', 'completed', '2025-01-30 08:00:00'),
('course004', 'st003', 'completed', '2025-02-01 08:00:00'),
('course004', 'st003', 'completed', '2025-02-03 08:00:00'),
('course004', 'st003', 'completed', '2025-02-05 08:00:00'),
('course004', 'st003', 'completed', '2025-02-07 08:00:00'),
('course004', 'st003', 'completed', '2025-02-09 08:00:00'),
('course004', 'st003', 'completed', '2025-02-11 08:00:00'),
('course004', 'st003', 'completed', '2025-02-13 08:00:00');