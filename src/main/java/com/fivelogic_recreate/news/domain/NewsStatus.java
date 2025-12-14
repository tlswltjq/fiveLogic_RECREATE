package com.fivelogic_recreate.news.domain;

public enum NewsStatus {
    DRAFT {
        @Override
        public NewsStatus transitTo(NewsStatus target) {
            if (target == PROCESSING || target == DELETED) {
                return target;
            }
            throw invalidTransition(this, target);
        }
    }, PROCESSING {
        @Override
        public NewsStatus transitTo(NewsStatus target) {
            if (target == READY || target == DRAFT || target == DELETED) {
                return target;
            }
            throw invalidTransition(this, target);
        }
    }, READY {
        @Override
        public NewsStatus transitTo(NewsStatus target) {
            if (target == PUBLISHED || target == DELETED) {
                return target;
            }
            throw invalidTransition(this, target);
        }
    }, PUBLISHED {
        @Override
        public NewsStatus transitTo(NewsStatus target) {
            if (target == HIDDEN || target == DELETED) {
                return target;
            }
            throw invalidTransition(this, target);
        }
    }, HIDDEN {
        @Override
        public NewsStatus transitTo(NewsStatus target) {
            if (target == PUBLISHED || target == DELETED) {
                return target;
            }
            throw invalidTransition(this, target);
        }
    }, DELETED {
        @Override
        public NewsStatus transitTo(NewsStatus target) {
            if (target == DRAFT || target == PROCESSING || target == READY) {
                return target;
            }
            throw invalidTransition(this, target);
        }
    };

    public abstract NewsStatus transitTo(NewsStatus target);

    private static IllegalStateException invalidTransition(
            NewsStatus from,
            NewsStatus to
    ) {
        return new IllegalStateException(
                String.format(
                        "뉴스 상태를 %s에서 %s로 변경할 수 없습니다.",
                        from.name(),
                        to.name()
                )
        );
    }

    public static NewsStatus from(String value) {
        try {
            return NewsStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("유효하지 않은 상태입니다: " + value);
        }
    }
}
