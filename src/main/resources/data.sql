DELETE FROM store_item;
DELETE FROM product;
DELETE FROM store;

----------Insert data in store------------
INSERT INTO store(id, external_id, name, code, street_number, street_name, zip_code, city, country, created_by, created_on) VALUES
    (1, '472f35d1-a1cd-4919-80d0-ffc0d344a074', 'MyStores GmbH', '00S001BERDE', '10', 'Abcdstr', '10121', 'Berlin',     'DE', 'ADMIN', '2016-06-22 19:10:25'),
    (2, '5250b14c-c43e-4ab6-b5fb-4bac38790089', 'Raffael GmbH', '00S011MUNDE', '89', 'Efghstr', '80109',  'Munich',     'DE', 'ADMIN', '2017-08-12 19:10:25'),
    (3, 'da49b7dd-50fc-4bb4-a859-9dd30e6aae9a', 'Marcus GmbH', '00S061DUSDE', '55', 'Hijklstr', '40070',  'Dusseldorf', 'DE', 'ADMIN', '2023-01-07 19:10:25');

----------Insert data in product------------
INSERT INTO product(id, external_id, name, code, type, created_by, created_on) VALUES
    (1, '914c9981-06dd-4735-bc93-4f910fb82830', 'Product 011','00PR001INTL', 'VEGAN',   'ADMIN', '2023-01-07 19:10:25'),
    (2, '930b9ded-a5ea-4a93-993e-04240be43e5b', 'Product 233','00PR231INTL', 'VEG',     'ADMIN', '2023-01-07 19:10:25'),
    (3, '7b1ed512-bf62-4a78-b75c-68ae4e031472', 'Product 678','00PR908INTL', 'NON_VEG', 'ADMIN', '2023-01-07 19:10:25');

----------Insert data in store_item------------
INSERT INTO store_item(id, external_id, name, store_id, product_id, item_quantity, created_by, created_on) VALUES
    (1, '2a088ca8-32eb-4b18-aa18-e617121837a1', 'item 010', 1, 3, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (2, '90a4af14-0dd2-4637-bdf2-b4a7ee427869', 'item 011', 1, 2, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (3, '5ce7af89-4cac-4b12-a870-eb04175c2640', 'item 012', 1, 1, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (4, 'e69938ba-9853-4181-babb-5ec21b73199a', 'item 300', 2, 2, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (5, 'd05d0a40-4ad2-46a3-8e10-dda11727a1ee', 'item 315', 2, 3, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (6, '97b02aee-3acb-4093-afa5-e630ae82cbb7', 'item 455', 2, 2, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (7, 'cfd7d87c-c467-46cc-9964-b29fabcd600f', 'item 677', 3, 3, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (8, '3529ff4c-3c35-4b9b-a954-65b725343602', 'item 555', 3, 1, 100, 'ADMIN', '2023-01-07 19:10:25'),
    (9, 'c6e07a99-a6ff-4b3a-8add-258648e43580', 'item 141', 3, 1, 100, 'ADMIN', '2023-01-07 19:10:25');
