insert into
    users ( email, login, password, enabled )
values
    ('headadmin@tastetracker.com', 'admin' , '{noop}admin', true),
    ('exampleuser@tastetracker.com', 'user', '{noop}user', true),
    ('exampleeditor@tastetracker.com', 'editor', '{noop}editor', true);

insert into
    user_role ( name, description )
values
    ('ADMIN', 'pełne uprawnienia'),   -- 1
    ('USER', 'podstawowe uprawnienia, możliwość dodawania recenzji'),   -- 2
    ('EDITOR', 'podstawowe uprawnienia + możliwość zarządzania treściami');   -- 3

insert into
    user_roles (user_id, role_id)
values
    (1, 1),
    (2, 2),
    (3, 3);