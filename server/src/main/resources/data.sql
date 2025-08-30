-- Insert Subscription Plans
INSERT INTO subscription_plan(id, name, code, price, currency, interval, description, active, number_of_days, created_at)
values (1, 'Free Trial', 'FREE_TRIAL', 0.00, 'INR', 'MONTHLY', 'Free trial plan with limited features', true, 5, CURRENT_TIMESTAMP);
INSERT INTO subscription_plan(id, name, code, price, currency, interval, description, active, number_of_days, badge, created_at)
values (2, 'Basic', 'BASIC', 499.00, 'INR', 'MONTHLY', 'Basic plan with essential features', true, 30, 'Recommended', CURRENT_TIMESTAMP);
INSERT INTO subscription_plan(id, name, code, price, currency, interval, description, active, number_of_days, created_at)
values (3, 'Basic Plus', 'BASIC_PLUS', 1499.00, 'INR', 'YEARLY', 'Basic Plus plan with additional features', true, 365, CURRENT_TIMESTAMP);
INSERT INTO subscription_plan(id, name, code, price, currency, interval, description, active, number_of_days, created_at)
values (4, 'Pro', 'PRO', 2999.00, 'INR', 'MONTHLY', 'Pro plan with all features', true, 30, CURRENT_TIMESTAMP);
INSERT INTO subscription_plan(id, name, code, price, currency, interval, description, active, number_of_days, badge, created_at)
values (5, 'Pro Plus', 'PRO_PLUS', 12999.00, 'INR', 'YEARLY', 'Pro plan with all features', true, 365, 'Most Popular', CURRENT_TIMESTAMP);
INSERT INTO subscription_plan(id, name, code, price, currency, interval, description, active, number_of_days, badge, created_at)
values (6, 'Enterprise', 'ENTERPRISE', 24999.00, 'INR', 'YEARLY', 'Enterprise plan with custom features', true, 365, 'Custom', CURRENT_TIMESTAMP);
INSERT INTO subscription_plan(id, name, code, price, currency, interval, description, active, number_of_days, badge, created_at)
values (7, 'Unlimited', 'UNLIMITED', 49999.00, 'INR', 'YEARLY', 'Unlimited plan with all features and priority support', true, 365, 'Unlimited', CURRENT_TIMESTAMP);

