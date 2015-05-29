use march4;

DELETE FROM quest;
DELETE FROM project;
DELETE FROM user;

INSERT INTO `user` VALUES (1,'a@naver.com','1234'),(2,'b@next.org','qwer'),(3,'fd@test.net','d2d#');
INSERT INTO `project` VALUES (1,2,'testProject',NULL),(2,2,'2made project 2',NULL),(3,1,'1 also made project',NULL);
INSERT INTO `quest` VALUES (1,2,0,0,1,'first test quest','2013-01-01 00:00:00'),(2,2,0,0,2,'SECOND quest','2014-12-25 20:24:35'),(3,3,0,10,1,'first quest in another project','1994-08-20 07:00:00');
