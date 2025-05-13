-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 11, 2025 at 09:35 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `giasutot`
--
CREATE DATABASE IF NOT EXISTS `giasutot` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `giasutot`;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
                           `m_acc` char(20) NOT NULL,
                           `name` varchar(100) DEFAULT NULL,
                           `pass` varchar(100) DEFAULT NULL,
                           `statusAcc` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`m_acc`, `name`, `pass`, `statusAcc`) VALUES
                                                                 ('ACC001', 'Lê Văn An', 'pass123', 'active'),
                                                                 ('ACC002', 'Nguyễn Thị B', 'b123', 'active'),
                                                                 ('ACC003', 'Trần Văn C', 'c123', 'active'),
                                                                 ('ACC004', 'Hoàng Thị D', 'd123', 'inactive'),
                                                                 ('ACC005', 'Phạm Văn E', 'e123', 'active'),
                                                                 ('ACC006', 'Nguyễn Văn F', 'f123', 'active'),
                                                                 ('ACC007', 'Trịnh Thị G', 'g123', 'active'),
                                                                 ('ACC008', 'Đào Văn H', 'h123', 'inactive'),
                                                                 ('ACC009', 'Bùi Thị I', 'i123', 'active'),
                                                                 ('ACC010', 'Lý Văn J', 'j123', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
                          `m_course` char(20) NOT NULL,
                          `m_sub` char(20) DEFAULT NULL,
                          `m_tutor` char(20) DEFAULT NULL,
                          `timeCourse` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`m_course`, `m_sub`, `m_tutor`, `timeCourse`) VALUES
                                                                        ('COUR001', 'SUB001', 'TUT001', '2025-05-15 18:00:00'),
                                                                        ('COUR002', 'SUB002', 'TUT002', '2025-05-16 19:00:00'),
                                                                        ('COUR003', 'SUB003', 'TUT003', '2025-05-17 17:30:00'),
                                                                        ('COUR004', 'SUB004', 'TUT004', '2025-05-18 14:00:00'),
                                                                        ('COUR005', 'SUB005', 'TUT005', '2025-05-19 10:00:00'),
                                                                        ('COUR006', 'SUB006', 'TUT006', '2025-05-20 08:00:00'),
                                                                        ('COUR007', 'SUB007', 'TUT007', '2025-05-21 09:30:00'),
                                                                        ('COUR008', 'SUB008', 'TUT008', '2025-05-22 13:00:00'),
                                                                        ('COUR009', 'SUB009', 'TUT009', '2025-05-23 11:00:00'),
                                                                        ('COUR010', 'SUB010', 'TUT010', '2025-05-24 15:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `lesson`
--

CREATE TABLE `lesson` (
                          `m_course` char(20) NOT NULL,
                          `m_st` char(20) NOT NULL,
                          `statusLess` varchar(50) DEFAULT NULL,
                          `TimeLess` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lesson`
--

INSERT INTO `lesson` (`m_course`, `m_st`, `statusLess`, `TimeLess`) VALUES
                                                                        ('COUR001', 'ST001', 'done', '2025-05-15 18:00:00'),
                                                                        ('COUR001', 'ST001', 'done', '2025-05-17 18:00:00'),
                                                                        ('COUR002', 'ST002', 'done', '2025-05-16 19:00:00'),
                                                                        ('COUR003', 'ST003', 'waiting', '2025-05-17 17:30:00'),
                                                                        ('COUR004', 'ST004', 'done', '2025-05-18 14:00:00'),
                                                                        ('COUR005', 'ST005', 'cancel', '2025-05-19 10:00:00'),
                                                                        ('COUR006', 'ST006', 'done', '2025-05-20 08:00:00'),
                                                                        ('COUR007', 'ST007', 'done', '2025-05-21 09:30:00'),
                                                                        ('COUR008', 'ST008', 'waiting', '2025-05-22 13:00:00'),
                                                                        ('COUR009', 'ST009', 'done', '2025-05-23 11:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `registered_subjects`
--

CREATE TABLE `registered_subjects` (
                                       `m_course` char(20) NOT NULL,
                                       `m_st` char(20) NOT NULL,
                                       `registrationDate` date DEFAULT NULL,
                                       `numberOfLessons` int(11) DEFAULT NULL,
                                       `statusRSub` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `registered_subjects`
--

INSERT INTO `registered_subjects` (`m_course`, `m_st`, `registrationDate`, `numberOfLessons`, `statusRSub`) VALUES
                                                                                                                ('COUR001', 'ST001', '2025-05-10', 10, 'active'),
                                                                                                                ('COUR002', 'ST002', '2025-05-11', 8, 'active'),
                                                                                                                ('COUR003', 'ST003', '2025-05-12', 12, 'completed'),
                                                                                                                ('COUR004', 'ST004', '2025-05-13', 15, 'active'),
                                                                                                                ('COUR005', 'ST005', '2025-05-14', 10, 'inactive'),
                                                                                                                ('COUR006', 'ST006', '2025-05-15', 8, 'active'),
                                                                                                                ('COUR007', 'ST007', '2025-05-16', 12, 'active'),
                                                                                                                ('COUR008', 'ST008', '2025-05-17', 6, 'pending'),
                                                                                                                ('COUR009', 'ST009', '2025-05-18', 10, 'active'),
                                                                                                                ('COUR010', 'ST010', '2025-05-19', 14, 'active');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
                           `m_st` char(20) NOT NULL,
                           `name` varchar(100) DEFAULT NULL,
                           `birth` date DEFAULT NULL,
                           `describeSt` text DEFAULT NULL,
                           `m_acc` char(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`m_st`, `name`, `birth`, `describeSt`, `m_acc`) VALUES
                                                                           ('ST001', 'Mai Anh', '2007-10-15', 'Học sinh lớp 10', 'ACC001'),
                                                                           ('ST002', 'Tuấn Minh', '2006-09-20', 'Học sinh lớp 11', 'ACC002'),
                                                                           ('ST003', 'Lan Hương', '2008-01-25', 'Lớp 9', 'ACC003'),
                                                                           ('ST004', 'Hải Nam', '2005-05-10', 'Lớp 12 luyện thi', 'ACC004'),
                                                                           ('ST005', 'Ngọc Hà', '2007-08-05', 'Yêu thích tin học', 'ACC005'),
                                                                           ('ST006', 'Phú Quý', '2006-04-20', 'Lớp 11 khối A', 'ACC006'),
                                                                           ('ST007', 'Hồng Phúc', '2008-02-28', 'Lớp 9 chuyên Toán', 'ACC007'),
                                                                           ('ST008', 'Thiện Nhân', '2007-07-10', 'Lớp 10 ban C', 'ACC008'),
                                                                           ('ST009', 'Bảo Hân', '2006-11-13', 'Lớp 11 tiếng Anh', 'ACC009'),
                                                                           ('ST010', 'Minh Quân', '2005-12-30', 'Lớp 12A1', 'ACC010');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
                           `m_sub` char(20) NOT NULL,
                           `name` varchar(100) DEFAULT NULL,
                           `levelSub` varchar(50) DEFAULT NULL,
                           `describeSub` text DEFAULT NULL,
                           `fee` decimal(10,2) DEFAULT NULL,
                           `statusSub` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`m_sub`, `name`, `levelSub`, `describeSub`, `fee`, `statusSub`) VALUES
                                                                                           ('SUB001', 'Toán', 'Lớp 10', 'Toán cơ bản', 300000.00, 'active'),
                                                                                           ('SUB002', 'Vật lý', 'Lớp 11', 'Lý nâng cao', 350000.00, 'active'),
                                                                                           ('SUB003', 'Tiếng Anh', 'Lớp 9', 'Ngữ pháp & giao tiếp', 250000.00, 'active'),
                                                                                           ('SUB004', 'Hóa học', 'Lớp 12', 'Luyện thi THPTQG', 400000.00, 'active'),
                                                                                           ('SUB005', 'Tin học', 'Lớp 8', 'Python cơ bản', 320000.00, 'inactive'),
                                                                                           ('SUB006', 'Ngữ Văn', 'Lớp 12', 'Văn nghị luận', 280000.00, 'active'),
                                                                                           ('SUB007', 'Sinh học', 'Lớp 11', 'Sinh lý cơ bản', 310000.00, 'active'),
                                                                                           ('SUB008', 'Lịch sử', 'Lớp 10', 'Kiến thức cơ bản', 200000.00, 'active'),
                                                                                           ('SUB009', 'Địa lý', 'Lớp 11', 'Địa kinh tế', 220000.00, 'inactive'),
                                                                                           ('SUB010', 'IELTS', 'Lớp 12', 'Luyện thi 6.5+', 500000.00, 'active');

-- --------------------------------------------------------

--
-- Table structure for table `tutor`
--

CREATE TABLE `tutor` (
                         `m_tutor` char(20) NOT NULL,
                         `name` varchar(100) DEFAULT NULL,
                         `birth` date DEFAULT NULL,
                         `describeTutor` text DEFAULT NULL,
                         `m_acc` char(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tutor`
--

INSERT INTO `tutor` (`m_tutor`, `name`, `birth`, `describeTutor`, `m_acc`) VALUES
                                                                               ('TUT001', 'Nguyễn Văn A', '1990-05-12', 'GV Toán kinh nghiệm', 'ACC001'),
                                                                               ('TUT002', 'Trần Thị B', '1992-03-20', 'GV Lý vui tính', 'ACC002'),
                                                                               ('TUT003', 'Lê Văn C', '1988-11-10', 'GV tiếng Anh', 'ACC003'),
                                                                               ('TUT004', 'Phạm Thị D', '1995-09-01', 'GV Hóa chất lượng', 'ACC004'),
                                                                               ('TUT005', 'Vũ Quốc E', '1991-01-15', 'GV Tin học', 'ACC005'),
                                                                               ('TUT006', 'Ngô Thị F', '1987-08-10', 'GV Văn ôn thi đại học', 'ACC006'),
                                                                               ('TUT007', 'Đinh Văn G', '1989-12-30', 'GV Sinh học', 'ACC007'),
                                                                               ('TUT008', 'Hà Thị H', '1993-03-22', 'GV sử địa', 'ACC008'),
                                                                               ('TUT009', 'Dương Văn I', '1994-06-14', 'GV luyện thi IELTS', 'ACC009'),
                                                                               ('TUT010', 'Bạch Văn J', '1985-10-09', 'GV giải tích', 'ACC010');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
    ADD PRIMARY KEY (`m_acc`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
    ADD PRIMARY KEY (`m_course`),
  ADD KEY `m_sub` (`m_sub`),
  ADD KEY `m_tutor` (`m_tutor`);

--
-- Indexes for table `lesson`
--
ALTER TABLE `lesson`
    ADD PRIMARY KEY (`m_course`,`m_st`,`TimeLess`),
  ADD KEY `m_st` (`m_st`);

--
-- Indexes for table `registered_subjects`
--
ALTER TABLE `registered_subjects`
    ADD PRIMARY KEY (`m_course`,`m_st`),
  ADD KEY `m_st` (`m_st`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
    ADD PRIMARY KEY (`m_st`),
  ADD KEY `m_acc` (`m_acc`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
    ADD PRIMARY KEY (`m_sub`);

--
-- Indexes for table `tutor`
--
ALTER TABLE `tutor`
    ADD PRIMARY KEY (`m_tutor`),
  ADD KEY `m_acc` (`m_acc`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `course`
--
ALTER TABLE `course`
    ADD CONSTRAINT `course_ibfk_1` FOREIGN KEY (`m_sub`) REFERENCES `subject` (`m_sub`),
  ADD CONSTRAINT `course_ibfk_2` FOREIGN KEY (`m_tutor`) REFERENCES `tutor` (`m_tutor`);

--
-- Constraints for table `lesson`
--
ALTER TABLE `lesson`
    ADD CONSTRAINT `lesson_ibfk_1` FOREIGN KEY (`m_course`) REFERENCES `course` (`m_course`),
  ADD CONSTRAINT `lesson_ibfk_2` FOREIGN KEY (`m_st`) REFERENCES `student` (`m_st`);

--
-- Constraints for table `registered_subjects`
--
ALTER TABLE `registered_subjects`
    ADD CONSTRAINT `registered_subjects_ibfk_1` FOREIGN KEY (`m_course`) REFERENCES `course` (`m_course`),
  ADD CONSTRAINT `registered_subjects_ibfk_2` FOREIGN KEY (`m_st`) REFERENCES `student` (`m_st`);

--
-- Constraints for table `student`
--
ALTER TABLE `student`
    ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`m_acc`) REFERENCES `account` (`m_acc`);

--
-- Constraints for table `tutor`
--
ALTER TABLE `tutor`
    ADD CONSTRAINT `tutor_ibfk_1` FOREIGN KEY (`m_acc`) REFERENCES `account` (`m_acc`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;