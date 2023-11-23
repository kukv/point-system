package jp.kukv.point.domain.model.transaction

/**
 * 取引イベント
 */
enum class Event {
    獲得,
    獲得キャンセル,
    利用,
    利用キャンセル,
    失効,
}
