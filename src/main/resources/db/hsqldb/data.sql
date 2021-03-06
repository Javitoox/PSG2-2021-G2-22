-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner2','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (4,'owner2','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner3','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (5,'owner3','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner4','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (6,'owner4','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner5','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (7,'owner5','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner6','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (8,'owner6','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner7','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (9,'owner7','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner8','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (10,'owner8','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner9','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (11,'owner9','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner10','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (12,'owner10','owner');
INSERT INTO users(username,password,enabled) VALUES ('owner11','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (13,'owner11','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (3,'vet1','veterinarian');

INSERT INTO vets(id, first_name, last_name)  VALUES (1, 'James', 'Carter');
INSERT INTO vets(id, first_name, last_name)  VALUES (2, 'Helen', 'Leary');
INSERT INTO vets(id, first_name, last_name)  VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets(id, first_name, last_name)  VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets(id, first_name, last_name)  VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets(id, first_name, last_name) VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiologia');
INSERT INTO specialties VALUES (2, 'cirugia');
INSERT INTO specialties VALUES (3, 'odontologia');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'gato');
INSERT INTO types VALUES (2, 'perro');
INSERT INTO types VALUES (3, 'lagarto');
INSERT INTO types VALUES (4, 'serpiente');
INSERT INTO types VALUES (5, 'ave');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner2');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner3');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner4');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner5');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner6');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner7');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner8');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner9');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner10');
INSERT INTO owners VALUES (11, 'Javi', 'Tecca', 'Wall Street', 'Yakson', '6085555400', 'owner11');

INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (1, 'Leo', '2010-09-07', 1, 1,TRUE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (2, 'Basil', '2012-08-06', 6, 2, TRUE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (3, 'Rosy', '2011-04-17', 2, 3, FALSE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (4, 'Jewel', '2010-03-07', 2, 3, TRUE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (5, 'Iggy', '2010-11-30', 3, 4, TRUE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (6, 'George', '2010-01-20', 4, 5, FALSE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (7, 'Samantha', '2012-09-04', 1, 6, FALSE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (8, 'Max', '2012-09-04', 1, 6, TRUE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (9, 'Lucky', '2011-08-06', 5, 7, FALSE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (10, 'Mulligan', '2007-02-24', 2, 8, FALSE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (11, 'Freddy', '2010-03-09', 5, 9, FALSE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (12, 'Lucky', '2010-06-24', 2, 10, FALSE);
INSERT INTO pets(id,name,birth_date,type_id,owner_id,in_adoption) VALUES (13, 'Sly', '2012-06-08', 1, 10, FALSE);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO reservations(id,start,end,special_cares,level,pet_id) VALUES (1, '2030-8-13', '2030-8-20', 'Food with proteins', 'STANDARD', '1');
INSERT INTO reservations(id,start,end,special_cares,level,pet_id) VALUES (2, '2030-9-13', '2030-10-20', '', 'VIP', '2');
INSERT INTO reservations(id,start,end,special_cares,level,pet_id) VALUES (3, '2031-8-13', '2031-8-20', 'Food with a lot of vitamines', 'STANDARD', '5');

INSERT INTO causes(id,name,description,organization,goal,owner_id,donations) VALUES (1,'Operaci??n pierna', 'Operaci??n de la pierna para un perro cojo', 'Dogs', 155.5, 1, 0);
INSERT INTO causes(id,name,description,organization,goal,owner_id,donations) VALUES (2,'??Todos con Leo!', 'Recaudaci??n de fondos para operar a Leo tras su accidente', 'Your dog, our life', 200, 1, 0);
INSERT INTO causes(id,name,description,organization,goal,owner_id,donations) VALUES (3,'Parque para Basil', 'Recaudaci??n de fondos para darle a Basil un espacio en el que poder pasear y superar su miedo a desconocidos', 'We help to yours pets', 500, 2, 0);

INSERT INTO adoptions(owner,possible_owner,description,adoption_state_type, pet_id) VALUES ('owner1','owner2','I am a dog',2,'3');
INSERT INTO adoptions(owner,possible_owner,description, adoption_state_type, pet_id) VALUES ('owner11','owner1','I am a cat',2,'1');

