// 数组删除
Array.prototype.remove = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
}

// 数组上移
Array.prototype.up = function (index) {
    if (index === 0) {
        return
    }
    // 在上一项插入该项
    this.splice(index - 1, 0, (this[index]))
    // 删除后一项
    this.splice(index + 1, 1)
}

// 数组下移
Array.prototype.down = function (index) {
    if (index === (this.length - 1)) {
        return
    }
    // 在下一项插入该项
    this.splice(index + 2, 0, (this[index]))
    // 删除前一项
    this.splice(index, 1)
}
