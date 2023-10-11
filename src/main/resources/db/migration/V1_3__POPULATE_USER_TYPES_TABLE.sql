INSERT INTO public.user_types(type_name, description)
SELECT 'ADMIN','Adds cars'
WHERE NOT EXISTS (
    SELECT 1
    FROM public.user_types
    WHERE type_name = 'Admin'
);

INSERT INTO public.user_types(type_name, description)
SELECT 'USER','Buys cars'
WHERE NOT EXISTS (
    SELECT 1
    FROM public.user_types
    WHERE type_name = 'User'
);