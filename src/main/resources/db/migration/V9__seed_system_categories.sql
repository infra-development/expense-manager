-- Phase 2: seed SYSTEM categories (INCOME and EXPENSE)

INSERT INTO categories (scope, name, description) VALUES
    ('SYSTEM', 'Food',           'Food and groceries'),
    ('SYSTEM', 'Transportation', 'Transport and travel'),
    ('SYSTEM', 'Shopping',       'General shopping'),
    ('SYSTEM', 'Entertainment',  'Entertainment and leisure'),
    ('SYSTEM', 'Healthcare',     'Medical and healthcare'),
    ('SYSTEM', 'Education',      'Education and learning'),
    ('SYSTEM', 'Salary',         'Salary and wages'),
    ('SYSTEM', 'Investment',     'Investment and savings'),
    ('SYSTEM', 'Other Expense',  'Other expenses'),
    ('SYSTEM', 'Other Income',   'Other incomes');
