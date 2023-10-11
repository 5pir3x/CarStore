ALTER TABLE IF EXISTS public.users
    ADD CONSTRAINT user_type_fk FOREIGN KEY (user_type_id)
        REFERENCES public.user_types (id);