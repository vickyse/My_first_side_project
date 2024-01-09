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

DELETE FROM hole_wash
WHERE (size, depth) = (1, 105); 
-- 在測試hole_wash testAddNewHoleDetails之後執行。

INSERT INTO hole_wash (size, depth, price, basic_revenue) VALUES
(1, 15, 350, 1000);
-- 在測試hole_wash testDeleteHole之後執行。

UPDATE hole_wash
SET price = 1000
WHERE (size, depth) = (1, 15);
-- 在測試hole_wash testEditHolePrice之後執行。