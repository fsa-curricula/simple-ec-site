INSERT INTO users (name, email, password, postal_code, address, role, created_at, updated_at) VALUES
     ('田中 太郎', 'user01@example.jp', '$2a$10$7NurUE4kK/xyWxlf2EA62.xu2LFYnZJDMBJR/Y.5IChJEzWrajNda', '100-0001', '東京都千代田区千代田1-1-1', 'ROLE_USER', '2024-10-16 16:15:14.778155', '2024-10-16 16:15:14.778155'),
     ('高橋 一郎', 'admin01@example.jp', '$2a$10$rAtb4JMGYhJhFPCqqCZe9u56gdXSsVDF9gCtRThEva9jf6nwMRrUm', '100-0001', '東京都千代田区千代田1-1-1', 'ROLE_ADMIN', '2024-10-16 16:16:23.633987', '2024-10-16 16:16:23.633987');
INSERT INTO products (name, description, price, image_url, created_at, updated_at) VALUES
     ('青い傘', 'シンプルな青い傘です。これで雨の日も大丈夫です。', 1000, 'https://i.gyazo.com/8534d1a650e71541a0381d4875f28996.jpg', '2024-10-25 15:42:58.589943', '2024-10-25 15:42:58.589943'),
     ('レザーバッグ', 'レトロなレザーバッグです。おしゃれです。', 30000, 'https://i.gyazo.com/cb66077fd007265ec38a1cccef0d26ec.jpg', '2024-10-25 19:54:34.997086', '2024-10-25 19:54:34.997086');