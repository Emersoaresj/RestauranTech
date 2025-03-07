CREATE TABLE IF NOT EXISTS public.tipos_usuario (
    id_tipo_usuario SERIAL PRIMARY KEY,
    nome_tipo VARCHAR(255) NOT NULL
);

INSERT INTO public.tipos_usuario (nome_tipo)
VALUES
    ('Cliente'),
    ('Dono de Restaurante');
