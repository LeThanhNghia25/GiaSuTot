-- Bảng account
CREATE TABLE account (
                         id CHAR(20) PRIMARY KEY, -- Đổi từ id_acc thành id
                         email VARCHAR(100) NOT NULL,
                         password VARCHAR(100) NOT NULL, -- Đổi từ pass thành password
                         role INT DEFAULT 1 CHECK (role IN (1, 2, 3)), -- 1: student, 2: tutor, 3: admin
                         status VARCHAR(50) NOT NULL CHECK (status IN ('active', 'inactive')) -- Đổi từ status_acc thành status
);

-- Bảng student
CREATE TABLE student (
                         id CHAR(20) PRIMARY KEY, -- Đổi từ id_st thành id
                         name VARCHAR(100) NOT NULL,
                         birth DATE NOT NULL,
                         description TEXT, -- Đổi từ describe_st thành description
                         account_id CHAR(20), -- Đổi từ id_acc thành account_id
                         FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Bảng subject
CREATE TABLE subject (
                         id CHAR(20) PRIMARY KEY, -- Đổi từ id_sub thành id
                         name VARCHAR(100) NOT NULL,
                         level VARCHAR(50) NOT NULL,
                         description TEXT, -- Đổi từ describe_sb thành description
                         fee DECIMAL(10, 2) NOT NULL,
                         status VARCHAR(50) NOT NULL CHECK (status IN ('active', 'inactive')) -- Đổi từ status_sub thành status
);

-- Bảng tutor
CREATE TABLE tutor (
                       id CHAR(20) PRIMARY KEY, -- Đổi từ id_tutor thành id
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL,
                       birth DATE NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       specialization VARCHAR(255) NOT NULL,
                       description TEXT, -- Đổi từ describe_tutor thành description
                       id_card_number INT(12) NOT NULL, -- Đổi từ cccd thành id_card_number
                       bank_account_number INT(15) NOT NULL, -- Đổi từ bank_code thành bank_account_number
                       bank_name VARCHAR(255) NOT NULL,
                       account_id CHAR(20), -- Đổi từ id_acc thành account_id
                       evaluate INT CHECK (evaluate BETWEEN 1 AND 5),
                       FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Bảng course
CREATE TABLE course (
                        id CHAR(20) PRIMARY KEY, -- Đổi từ id_course thành id
                        subject_id CHAR(20), -- Đổi từ id_sub thành subject_id
                        tutor_id CHAR(20), -- Đổi từ id_tutor thành tutor_id
                        time DATETIME NOT NULL, -- Đổi từ time_course thành time
                        FOREIGN KEY (subject_id) REFERENCES subject(id),
                        FOREIGN KEY (tutor_id) REFERENCES tutor(id)
);

-- Bảng registered_subjects
CREATE TABLE registered_subjects (
                                     course_id CHAR(20), -- Đổi từ id_course thành course_id
                                     student_id CHAR(20), -- Đổi từ id_st thành student_id
                                     registration_date DATE NOT NULL,
                                     number_of_lessons INT NOT NULL,
                                     status VARCHAR(50) NOT NULL CHECK (status IN ('registered', 'completed', 'cancelled', 'pending_payment')), -- Đổi từ status_rsub thành status
                                     PRIMARY KEY (course_id, student_id),
                                     FOREIGN KEY (course_id) REFERENCES course(id),
                                     FOREIGN KEY (student_id) REFERENCES student(id)
);

-- Bảng lesson
CREATE TABLE lesson (
                        course_id CHAR(20), -- Đổi từ id_course thành course_id
                        student_id CHAR(20), -- Đổi từ id_st thành student_id
                        status VARCHAR(50) NOT NULL CHECK (status IN ('completed', 'absent', 'scheduled')), -- Đổi từ status_less thành status
                        time DATETIME NOT NULL, -- Đổi từ time_less thành time
                        PRIMARY KEY (course_id, student_id, time),
                        FOREIGN KEY (course_id) REFERENCES course(id),
                        FOREIGN KEY (student_id) REFERENCES student(id)
);

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
                                                                   ('st001', 'Nguyen Van Nghia', '2005-01-01', 'Yeu thich Toan', 'acc001'),
                                                                   ('st002', 'Le Thi Lien', '2006-03-15', 'Hoc sinh gioi Van', 'acc002'),
                                                                   ('st003', 'Tran Van Nho', '2004-07-21', 'Thich hoc nhom', 'acc003'),
                                                                   ('st004', 'Pham Thi Dung', '2005-10-05', 'Nang dong, tu tin', 'acc010'),
                                                                   ('st005', 'Nguyen Trung Nhan', '2005-01-01', 'Yeu thich Toan', 'acc011'),
                                                                   ('st006', 'Truong Thi Mai', '2006-03-15', 'Hoc sinh gioi Van', 'acc012'),
                                                                   ('st007', 'Tran Dan', '2004-07-21', 'Thich hoc nhom', 'acc013'),
                                                                   ('st008', 'Le Trung Dung', '2005-10-05', 'Nang dong, tu tin', 'acc014');

-- Chèn dữ liệu vào bảng tutor (sửa id_card_number và bank_account_number cho hợp lý)
INSERT INTO tutor (id, name, email, birth, phone, address, specialization, description, id_card_number, bank_account_number, bank_name, account_id, evaluate) VALUES
                                                                                                                                                                  ('tut001', 'Nguyen Tuan Canh', 'tut1@example.com', '1990-01-01', '0901000001', 'Ha Noi', 'Toan', '10 nam kinh nghiem day Toan', 123456789012, 123456789012345, 'bidv', 'acc004', 5),
                                                                                                                                                                  ('tut002', 'Tran Thi Mai', 'tut2@example.com', '1988-05-12', '0901000002', 'HCM', 'Tieng Anh', 'Chuyen luyen giao tiep', 123456789013, 123456789012346, 'sacombank', 'acc005', 4),
                                                                                                                                                                  ('tut003', 'Le Hoang Minh', 'tut3@example.com', '1992-07-07', '0901000003', 'Da Nang', 'Hoa hoc', 'GV truong chuyen', 123456789014, 123456789012347, 'techcombank', 'acc006', 3),
                                                                                                                                                                  ('tut004', 'Pham Minh Huong', 'tut4@example.com', '1991-09-20', '0901000004', 'Hue', 'Toan', 'Nhiet huyet, vui ve', 123456789015, 123456789012348, 'mb', 'acc009', 4),
                                                                                                                                                                  ('tut005', 'Nguyen Thu', 'tut5@example.com', '1990-01-01', '0901000001', 'Ha Noi', 'Toan', '10 nam kinh nghiem day Toan', 123456789016, 123456789012349, 'tp bank', 'acc015', 5),
                                                                                                                                                                  ('tut006', 'Truong Cao Dat', 'tut6@example.com', '1988-05-12', '0901000002', 'HCM', 'Tieng Anh', 'Chuyen luyen giao tiep', 123456789017, 123456789012350, 'agribank', 'acc016', 4),
                                                                                                                                                                  ('tut007', 'Dinh Thi Ngoc', 'tut7@example.com', '1992-07-07', '0901000003', 'Da Nang', 'Hoa hoc', 'GV truong chuyen', 123456789018, 123456789012351, 'bidv', 'acc017', 3),
                                                                                                                                                                  ('tut008', 'Le Nghia', 'tut8@example.com', '1991-09-20', '0901000004', 'Hue', 'Toan', 'Nhiet huyet, vui ve', 123456789019, 123456789012352, 'mb', 'acc018', 4),
                                                                                                                                                                  ('tut009', 'Tran Nguyen Ven', 'tut9@example.com', '1988-05-12', '0901000002', 'HCM', 'Tieng Anh', 'Chuyen luyen giao tiep', 123456789020, 123456789012353, 'sacombank', 'acc019', 4),
                                                                                                                                                                  ('tut010', 'Mai Hanh Phuc', 'tut10@example.com', '1992-07-07', '0901000003', 'Da Nang', 'Hoa hoc', 'GV truong chuyen', 123456789021, 123456789012354, 'tp bank', 'acc020', 3);

-- Chèn dữ liệu vào bảng subject (sửa level và description cho nhất quán)
INSERT INTO subject (id, name, level, description, fee, status) VALUES
                                                                    ('sub001', 'Toan', 'Lop 10', 'Hoc Toan nang cao lop 10', 200000.00, 'active'),
                                                                    ('sub002', 'Tieng Anh', 'Giao tiep', 'Tieng Anh giao tiep co ban', 180000.00, 'active'),
                                                                    ('sub003', 'Hoa hoc', 'Lop 10', 'Hoc Hoa hoc co ban lop 10', 190000.00, 'inactive'),
                                                                    ('sub004', 'Vat ly', 'Lop 12', 'Hoc Vat ly nang cao lop 12', 200000.00, 'active'), -- Sửa level
                                                                    ('sub005', 'Ngu van', 'Lop 11', 'Hoc Ngu van nang cao lop 11', 200000.00, 'active'),
                                                                    ('sub006', 'Sinh hoc', 'Lop 10', 'Hoc Sinh hoc co ban lop 10', 190000.00, 'inactive'),
                                                                    ('sub007', 'Toan', 'Lop 5', 'Hoc Toan nang cao lop 5', 200000.00, 'active'),
                                                                    ('sub008', 'Tieng Anh', 'Lop 5', 'Tieng Anh giao tiep nang cao lop 5', 180000.00, 'active'),
                                                                    ('sub009', 'Toan', 'Lop 6', 'Hoc Toan co ban lop 6', 190000.00, 'inactive'),
                                                                    ('sub010', 'Vat ly', 'Lop 11', 'Hoc Vat ly nang cao lop 11', 200000.00, 'active'), -- Sửa description
                                                                    ('sub011', 'Hoa hoc', 'Lop 9', 'Hoc Hoa hoc co ban lop 9', 180000.00, 'active'),
                                                                    ('sub012', 'Sinh hoc', 'Lop 8', 'Hoc Sinh hoc co ban lop 8', 190000.00, 'inactive'),
                                                                    ('sub013', 'Toan', 'Lop 10', 'Hoc Toan nang cao lop 10', 200000.00, 'active'),
                                                                    ('sub014', 'Tieng Anh', 'Lop 10', 'Tieng Anh co ban lop 10', 180000.00, 'active'),
                                                                    ('sub015', 'Hoa hoc', 'Lop 7', 'Hoc Hoa hoc co ban lop 7', 190000.00, 'inactive');

-- Chèn dữ liệu vào bảng course
INSERT INTO course (id, subject_id, tutor_id, time) VALUES
                                                        ('course001', 'sub001', 'tut001', '2025-05-01 08:00:00'),
                                                        ('course002', 'sub002', 'tut002', '2025-05-02 09:00:00'),
                                                        ('course003', 'sub003', 'tut003', '2025-05-03 10:00:00'),
                                                        ('course004', 'sub004', 'tut004', '2025-01-10 08:00:00'),
                                                        ('course005', 'sub005', 'tut005', '2025-02-15 09:00:00'),
                                                        ('course006', 'sub001', 'tut001', '2025-03-01 08:00:00'),
                                                        ('course007', 'sub002', 'tut006', '2025-04-05 09:00:00'),
                                                        ('course008', 'sub011', 'tut007', '2025-04-10 10:00:00'),
                                                        ('course009', 'sub014', 'tut009', '2025-05-12 09:00:00');

-- Chèn dữ liệu vào bảng registered_subjects
INSERT INTO registered_subjects (course_id, student_id, registration_date, number_of_lessons, status) VALUES
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