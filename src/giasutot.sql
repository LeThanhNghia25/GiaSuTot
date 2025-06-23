-- Tắt kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 0;

-- Xóa các bảng con trước (bảng có khóa ngoại tham chiếu đến bảng khác)
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS registered_subjects;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS notifications;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS tutor;
DROP TABLE IF EXISTS student;
-- Cuối cùng xóa bảng cha
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS account;

-- Tạo lại bảng account
CREATE TABLE account (
                         id CHAR(20) PRIMARY KEY,
                         email VARCHAR(100) NOT NULL,
                         password VARCHAR(100) NOT NULL,
                         role INT DEFAULT 1 CHECK (role IN (1, 2, 3)),
                         status VARCHAR(50) NOT NULL CHECK (status IN ('active', 'inactive'))
);

-- Tạo lại bảng student
CREATE TABLE student (
                         id CHAR(20) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         birth DATE NOT NULL,
                         description TEXT,
                         account_id CHAR(20),
                         FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Tạo lại bảng subject
CREATE TABLE subject (
                         id CHAR(20) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         level VARCHAR(50) NOT NULL,
                         description TEXT,
                         fee DECIMAL(12) NOT NULL,
                         status VARCHAR(50) NOT NULL CHECK (status IN ('active', 'inactive'))
);

-- Tạo lại bảng tutor
CREATE TABLE tutor (
                       id CHAR(20) PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       birth DATE NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       specialization VARCHAR(255) NOT NULL,
                       description TEXT,
                       id_card_number BIGINT(12) NOT NULL,
                       bank_account_number BIGINT(15) NOT NULL,
                       bank_name VARCHAR(255) NOT NULL,
                       account_id CHAR(20),
                       evaluate INT CHECK (evaluate BETWEEN 1 AND 5),
                       FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Tạo lại bảng course
CREATE TABLE course (
                        id CHAR(20) PRIMARY KEY,
                        subject_id CHAR(20),
                        tutor_id CHAR(20),
                        time DATETIME NOT NULL,
                        FOREIGN KEY (subject_id) REFERENCES subject(id),
                        FOREIGN KEY (tutor_id) REFERENCES tutor(id)
);

-- Tạo lại bảng registered_subjects
CREATE TABLE registered_subjects (
                                     course_id CHAR(20),
                                     student_id CHAR(20),
                                     registration_date DATE NOT NULL,
                                     number_of_lessons INT NOT NULL,
                                     status VARCHAR(50) NOT NULL CHECK (status IN ('pending_payment', 'pending_approval', 'registered', 'cancelled', 'completed', 'trial')),
                                     PRIMARY KEY (course_id, student_id),
                                     FOREIGN KEY (course_id) REFERENCES course(id),
                                     FOREIGN KEY (student_id) REFERENCES student(id)
);

-- Tạo lại bảng lesson
CREATE TABLE lesson (
                        course_id CHAR(20),
                        student_id CHAR(20),
                        status VARCHAR(50) NOT NULL CHECK (status IN ('completed', 'absent', 'scheduled')),
                        time DATETIME NOT NULL,
                        PRIMARY KEY (course_id, student_id, time),
                        FOREIGN KEY (course_id) REFERENCES course(id),
                        FOREIGN KEY (student_id) REFERENCES student(id)
);

-- Tạo lại bảng notifications
CREATE TABLE notifications (
                               id CHAR(36) PRIMARY KEY,
                               account_id CHAR(20) NOT NULL,
                               message TEXT NOT NULL,
                               created_at DATETIME NOT NULL,
                               status VARCHAR(50) NOT NULL CHECK (status IN ('sent', 'pending', 'failed')),
                               FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Tạo lại bảng payment
CREATE TABLE payment (
                         id CHAR(20) PRIMARY KEY,
                         course_id CHAR(20) NOT NULL,
                         tutor_id CHAR(20) NOT NULL,
                         student_id CHAR(20) NOT NULL,
                         amount DECIMAL(12) NOT NULL,
                         payment_date DATETIME NOT NULL,
                         file_name VARCHAR(255) NULL,
                         file_path VARCHAR(255) NULL,
                         FOREIGN KEY (course_id) REFERENCES course(id),
                         FOREIGN KEY (tutor_id) REFERENCES tutor(id),
                         FOREIGN KEY (student_id) REFERENCES student(id)
);

CREATE TABLE tutor_requests (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                account_id CHAR(20) NOT NULL,
                                name VARCHAR(100) NOT NULL,
                                birth VARCHAR(10) NOT NULL, -- Lưu định dạng yyyy-MM-dd
                                email VARCHAR(100) NOT NULL,
                                phone VARCHAR(20) NOT NULL,
                                id_card_number BIGINT(12) NOT NULL,
                                bank_account_number BIGINT(15) NOT NULL,
                                bank_name VARCHAR(255) NOT NULL,
                                address VARCHAR(255) NOT NULL,
                                specialization VARCHAR(255) NOT NULL,
                                description VARCHAR(255) NOT NULL,
                                created_at DATETIME NOT NULL,
                                FOREIGN KEY (account_id) REFERENCES account(id)
);
-- tạo ban đánh giá
CREATE TABLE reviews (
                         tutor_id CHAR(10) NOT NULL,
                         course_id CHAR(10) NOT NULL,
                         student_id CHAR(10) NOT NULL,
                         time DATETIME DEFAULT CURRENT_TIMESTAMP,
                         comment TEXT,
                         FOREIGN KEY (course_id) REFERENCES course(id),
                         FOREIGN KEY (tutor_id) REFERENCES tutor(id),
                         FOREIGN KEY (student_id) REFERENCES student(id)
);
-- Bật lại kiểm tra khóa ngoại
SET FOREIGN_KEY_CHECKS = 1;

-- Chèn dữ liệu vào bảng account
INSERT INTO account (id, email, password, role, status) VALUES
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

-- Chèn dữ liệu vào bảng student
INSERT INTO student (id, name, birth, description, account_id) VALUES
                                                                   ('st001', 'Nguyễn Văn Nghĩa', '2005-01-01', 'Yêu thích Toán', 'acc001'),
                                                                   ('st002', 'Lê Thị Liên', '2006-03-15', 'Học sinh giỏi Văn', 'acc002'),
                                                                   ('st003', 'Trần Văn Nhỏ', '2004-07-21', 'Thích học nhóm', 'acc003'),
                                                                   ('st004', 'Phạm Thị Dung', '2005-10-05', 'Năng động, tự tin', 'acc010'),
                                                                   ('st005', 'Nguyễn Trung Nhân', '2005-01-01', 'Yêu thích Toán', 'acc011'),
                                                                   ('st006', 'Trương Thị Mai', '2006-03-15', 'Học sinh giỏi Văn', 'acc012'),
                                                                   ('st007', 'Trần Đan', '2004-07-21', 'Thích học nhóm', 'acc013'),
                                                                   ('st008', 'Lê Trung Dũng', '2005-10-05', 'Năng động, tự tin', 'acc014');

-- Chèn dữ liệu vào bảng tutor
INSERT INTO tutor (id, name, email, birth, phone, address, specialization, description, id_card_number, bank_account_number, bank_name, account_id, evaluate) VALUES
                                                                                                                                                                  ('tut001', 'Nguyễn Tuấn Cảnh', 'tut1@example.com', '1990-01-01', '0901000001', 'Hà Nội', 'Toán', '10 năm kinh nghiệm dạy Toán', 123456789012, 123456789012345, 'bidv', 'acc004', 5),
                                                                                                                                                                  ('tut002', 'Trần Thị Mai', 'tut2@example.com', '1988-05-12', '0901000002', 'TP.HCM', 'Tiếng Anh', 'Chuyên luyện giao tiếp', 123456789013, 123456789012346, 'sacombank', 'acc005', 4),
                                                                                                                                                                  ('tut003', 'Lê Hoàng Minh', 'tut3@example.com', '1992-07-07', '0901000003', 'Đà Nẵng', 'Hóa học', 'Giáo viên trường chuyên', 123456789014, 123456789012347, 'techcombank', 'acc006', 3),
                                                                                                                                                                  ('tut004', 'Phạm Minh Hương', 'tut4@example.com', '1991-09-20', '0901000004', 'Huế', 'Toán', 'Nhiệt huyết, vui vẻ', 123456789015, 123456789012348, 'mb', 'acc009', 4),
                                                                                                                                                                  ('tut005', 'Nguyễn Thu', 'tut5@example.com', '1990-01-01', '0901000001', 'Hà Nội', 'Toán', '10 năm kinh nghiệm dạy Toán', 123456789016, 123456789012349, 'tp bank', 'acc015', 5),
                                                                                                                                                                  ('tut006', 'Trương Cao Đạt', 'tut6@example.com', '1988-05-12', '0901000002', 'TP.HCM', 'Tiếng Anh', 'Chuyên luyện giao tiếp', 123456789017, 123456789012350, 'agribank', 'acc016', 4),
                                                                                                                                                                  ('tut007', 'Đinh Thị Ngọc', 'tut7@example.com', '1992-07-07', '0901000003', 'Đà Nẵng', 'Hóa học', 'Giáo viên trường chuyên', 123456789018, 123456789012351, 'bidv', 'acc017', 3),
                                                                                                                                                                  ('tut008', 'Lê Nghĩa', 'tut8@example.com', '1991-09-20', '0901000004', 'Huế', 'Toán', 'Nhiệt huyết, vui vẻ', 123456789019, '123456789012352', 'mb', 'acc018', 4),
                                                                                                                                                                  ('tut009', 'Trần Nguyễn Vẹn', 'tut9@example.com', '1988-05-12', '0901000002', 'TP.HCM', 'Tiếng Anh', 'Chuyên luyện giao tiếp', 123456789020, 123456789012353, 'sacombank', 'acc019', 4),
                                                                                                                                                                  ('tut010', 'Mai Hạnh Phúc', 'tut10@example.com', '1992-07-07', '0901000003', 'Đà Nẵng', 'Hóa học', 'Giáo viên trường chuyên', 123456789021, 123456789012354, 'tp bank', 'acc020', 3);

-- Chèn dữ liệu vào bảng subject
INSERT INTO subject (id, name, level, description, fee, status) VALUES
                                                                    ('sub001', 'Toán', 'Lớp 10', 'Học Toán nâng cao lớp 10', 2000000, 'active'),
                                                                    ('sub002', 'Tiếng Anh', 'Giao tiếp', 'Tiếng Anh giao tiếp cơ bản', 1800000, 'active'),
                                                                    ('sub003', 'Hóa học', 'Lớp 10', 'Học Hóa học cơ bản lớp 10', 1900000, 'inactive'),
                                                                    ('sub004', 'Vật lý', 'Lớp 12', 'Học Vật lý nâng cao lớp 12', 2000000, 'active'),
                                                                    ('sub005', 'Ngữ văn', 'Lớp 11', 'Học Ngữ văn nâng cao lớp 11', 2000000, 'active'),
                                                                    ('sub006', 'Sinh học', 'Lớp 10', 'Học Sinh học cơ bản lớp 10', 1900000, 'inactive'),
                                                                    ('sub007', 'Toán', 'Lớp 5', 'Học Toán nâng cao lớp 5', 2000000, 'active'),
                                                                    ('sub008', 'Tiếng Anh', 'Lớp 5', 'Tiếng Anh giao tiếp nâng cao lớp 5', 1800000, 'active'),
                                                                    ('sub009', 'Toán', 'Lớp 6', 'Học Toán cơ bản lớp 6', 1900000, 'inactive'),
                                                                    ('sub010', 'Vật lý', 'Lớp 11', 'Học Vật lý nâng cao lớp 11', 2000000, 'active'),
                                                                    ('sub011', 'Hóa học', 'Lớp 9', 'Học Hóa học cơ bản lớp 9', 1800000, 'active'),
                                                                    ('sub012', 'Sinh học', 'Lớp 8', 'Học Sinh học cơ bản lớp 8', 1900000, 'inactive'),
                                                                    ('sub013', 'Toán', 'Lớp 10', 'Học Toán nâng cao lớp 10', 2000000, 'active'),
                                                                    ('sub014', 'Tiếng Anh', 'Lớp 10', 'Tiếng Anh cơ bản lớp 10', 1800000, 'active'),
                                                                    ('sub015', 'Hóa học', 'Lớp 7', 'Học Hóa học cơ bản lớp 7', 1900000, 'inactive'),
                                                                    ('sub016', 'Địa lý', 'Lớp 10', 'Học Địa lý cơ bản lớp 10', 1800000, 'active'),
                                                                    ('sub017', 'Giáo dục công dân', 'Lớp 12', 'Học Giáo dục công dân lớp 12', 1700000, 'active'),
                                                                    ('sub018', 'Lịch sử', 'Lớp 8', 'Học Lịch sử cơ bản lớp 8', 1600000, 'active');

-- Chèn dữ liệu vào bảng course (20 khóa học)
INSERT INTO course (id, subject_id, tutor_id, time) VALUES
                                                        ('course001', 'sub001', 'tut001', '2025-05-01 08:00:00'),
                                                        ('course002', 'sub002', 'tut002', '2025-05-02 09:00:00'),
                                                        ('course003', 'sub003', 'tut003', '2025-05-03 10:00:00'),
                                                        ('course004', 'sub004', 'tut004', '2025-01-10 08:00:00'),
                                                        ('course005', 'sub005', 'tut005', '2025-02-15 09:00:00'),
                                                        ('course006', 'sub006', 'tut001', '2025-03-01 08:00:00'),
                                                        ('course007', 'sub002', 'tut006', '2025-04-05 09:00:00'),
                                                        ('course008', 'sub011', 'tut007', '2025-04-10 10:00:00'),
                                                        ('course009', 'sub014', 'tut009', '2025-05-12 09:00:00'),
                                                        ('course010', 'sub005', 'tut002', '2025-06-01 10:00:00'),
                                                        ('course011', 'sub007', 'tut003', '2025-06-02 11:00:00'),
                                                        ('course012', 'sub008', 'tut004', '2025-06-03 12:00:00'),
                                                        ('course013', 'sub009', 'tut005', '2025-06-04 13:00:00'),
                                                        ('course014', 'sub010', 'tut006', '2025-06-05 14:00:00'),
                                                        ('course015', 'sub011', 'tut007', '2025-06-06 15:00:00'),
                                                        ('course016', 'sub012', 'tut008', '2025-06-07 16:00:00'),
                                                        ('course017', 'sub013', 'tut009', '2025-06-08 17:00:00'),
                                                        ('course018', 'sub014', 'tut010', '2025-06-09 18:00:00'),
                                                        ('course019', 'sub016', 'tut001', '2025-06-10 19:00:00'),
                                                        ('course020', 'sub017', 'tut002', '2025-06-11 20:00:00');

-- Chèn dữ liệu vào bảng registered_subjects
INSERT INTO registered_subjects (course_id, student_id, registration_date, number_of_lessons, status) VALUES
                                                                                                          ('course001', 'st001', '2025-04-25', 10, 'completed'),
                                                                                                          ('course002', 'st002', '2025-04-26', 8, 'completed'),
                                                                                                          ('course003', 'st003', '2025-04-27', 5, 'cancelled'),
                                                                                                          ('course001', 'st004', '2025-01-15', 12, 'completed'),
                                                                                                          ('course004', 'st005', '2025-01-20', 10, 'registered'),
                                                                                                          ('course005', 'st006', '2025-02-10', 8, 'completed'),
                                                                                                          ('course006', 'st007', '2025-03-05', 15, 'completed'),
                                                                                                          ('course007', 'st008', '2025-04-01', 10, 'registered'),
                                                                                                          ('course008', 'st001', '2025-04-15', 6, 'completed'),
                                                                                                          ('course009', 'st002', '2025-05-01', 8, 'completed'),
                                                                                                          ('course004', 'st003', '2025-01-25', 10, 'completed'),
                                                                                                          ('course005', 'st004', '2025-02-20', 8, 'cancelled'),
                                                                                                          ('course006', 'st005', '2025-03-10', 12, 'pending_payment'),
                                                                                                          ('course009', 'st006', '2025-05-05', 5, 'pending_payment'),
                                                                                                          ('course010', 'st005', '2025-05-30', 10, 'pending_approval'),
                                                                                                          ('course011', 'st006', '2025-05-31', 8, 'pending_approval'),
                                                                                                          ('course012', 'st007', '2025-06-01', 12, 'pending_approval'),
                                                                                                          ('course013', 'st008', '2025-06-02', 10, 'pending_approval');

-- Chèn dữ liệu vào bảng lesson
INSERT INTO lesson (course_id, student_id, status, time) VALUES
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
('course006', 'st007', 'completed', '2025-03-15 08:00:00'),
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
('course004', 'st003', 'completed', '2025-02-13 08:00:00'),
-- course010, st005: 10 buổi
('course010', 'st005', 'scheduled', '2025-06-01 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-03 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-05 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-07 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-09 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-11 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-13 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-15 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-17 10:00:00'),
('course010', 'st005', 'scheduled', '2025-06-19 10:00:00');


(các cột birth, description có thể null)
ALTER TABLE student
MODIFY birth DATE NULL;
ALTER TABLE student
MODIFY COLUMN description TEXT NULL;

(cài đặt lại chế độ tiếng việt cho name)
ALTER TABLE student
    MODIFY COLUMN name VARCHAR(100)
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci
    NOT NULL;
ALTER TABLE account ADD COLUMN reset_token VARCHAR(255);

// quan tâm
CREATE TABLE `interest` (
                            `id_st` char(20) NOT NULL,
                            `id_tt` char(20) NOT NULL,
                            PRIMARY KEY (`id_st`,`id_tt`),
                            KEY `fk_interest_tutor` (`id_tt`),
                            CONSTRAINT `fk_interest_student` FOREIGN KEY (`id_st`) REFERENCES `student` (`id`),
                            CONSTRAINT `fk_interest_tutor` FOREIGN KEY (`id_tt`) REFERENCES `tutor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
