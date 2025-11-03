CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) NOT NULL UNIQUE,
    display_name VARCHAR(200),
    email VARCHAR(255) UNIQUE,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    created_at timestamptz NOT NULL DEFAULT now()
);

CREATE TABLE tags (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE faqs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(500) NOT NULL,
    question TEXT NOT NULL,
    answer TEXT,
    category_id UUID NOT NULL REFERENCES categories(id) ON DELETE RESTRICT,
    created_by UUID REFERENCES users(id) ON DELETE SET NULL,
    updated_by UUID REFERENCES users(id) ON DELETE SET NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE faq_tags (
    faq_id UUID NOT NULL REFERENCES faqs(id) ON DELETE CASCADE,
    tag_id UUID NOT NULL REFERENCES tags(id) ON DELETE CASCADE,
    PRIMARY KEY (faq_id, tag_id)
);

-- Indexes for search and filtering
CREATE INDEX idx_faq_title_question ON faqs USING gin (to_tsvector('english', coalesce(title,'') || ' ' || coalesce(question,'')));
CREATE INDEX idx_faq_category ON faqs(category_id);
CREATE INDEX idx_tags_name ON tags (lower(name));
