/**
 * Ringbuffer is constructed with a fixed size of elements, which are pre-initialized with 0
 */
export class RingBuffer {
    size = 0;
    head = 0;
    data = [];

    constructor(size: Number) {
        for(var i = 0; i < this.size; i++) {
            this.data.push(0);
        }
    }

    /**
     * returns size of RingBuffer
     * @returns {number}: size of RingBuffer
     */
    getLength() {
        return this.size;
    }

    /**
     * pushes value into the buffer, overriding the item at head+1 and setting head to this position;
     * @param value: the value, which shall be stored
     */
    push(value) {
        this.head = (this.head + 1) % this.size;
        this.data[this.head] = value;
    };

    /**
     * gets the item at head - index postion (
     * @param index: 0 is the newest item, the higher the number, the longer the item is stored already
     * @returns the value at the given position
     */
    get(index) {
        var i = (this.head - index);
        i = (i < 0 ? i+this.size: i);
        return this.data[i];
    };
}