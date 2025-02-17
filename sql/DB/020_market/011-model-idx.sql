ALTER TABLE product_params
    ADD CONSTRAINT fk_product
        FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE product_params
    ADD CONSTRAINT fk_param
        FOREIGN KEY (param_id) REFERENCES param_dictionary(id);