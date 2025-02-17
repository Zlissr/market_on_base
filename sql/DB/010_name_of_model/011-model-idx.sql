CREATE INDEX "model__idx_id" ON model(id ASC);
CREATE INDEX "model__idx_login" ON model (name ASC);

grant select, insert, update, delete on model to topt_user;
