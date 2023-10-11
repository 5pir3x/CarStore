CREATE INDEX IF NOT EXISTS fki_user_type_fk
    ON public.users(user_type_id);