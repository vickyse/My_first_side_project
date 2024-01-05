DELETE FROM rebar
WHERE rebar_number = 15;
-- 測試testAddRebarDetails後執行。

INSERT INTO rebar(rebar_number, price_per_cm, basic_revenue) VALUES
(3, 1.0, 1000);

UPDATE cut
SET price_per_cm = 28
WHERE basic_revenue != 1;
-- 在測試rebar testEditCutPrice之後執行。

UPDATE rebar SET basic_revenue = 1000
WHERE rebar_number != -1;
-- 在測試rebar testEditBasicRevenue之後執行。

UPDATE cut SET basic_revenue = 2500
WHERE basic_revenue != -1;
-- 在測試cut testEditBasicRevenue之後執行。