-- Inserção de dados de exemplo para testes

-- Inserindo clientes
INSERT INTO clientes (nome, email, telefone) VALUES
('João Silva', 'joao.silva@email.com', '(11) 99999-1111'),
('Maria Santos', 'maria.santos@email.com', '(11) 99999-2222'),
('Pedro Oliveira', 'pedro.oliveira@email.com', '(11) 99999-3333'),
('Ana Costa', 'ana.costa@email.com', '(11) 99999-4444');

-- Inserindo garçons
INSERT INTO garcons (nome, matricula) VALUES
('Carlos Mendes', 'GAR0001'),
('Fernanda Lima', 'GAR0002'),
('Roberto Souza', 'GAR0003');

-- Inserindo pratos
INSERT INTO pratos (nome, preco) VALUES
('Pizza Margherita', 45.90),
('Hambúrguer Clássico', 32.50),
('Salada Caesar', 28.00),
('Sopa de Legumes', 18.50),
('Bife à Parmegiana', 42.00),
('Frango Grelhado', 38.90),
('Sobremesa do Chef', 15.00),
('Refrigerante Lata', 8.50);
